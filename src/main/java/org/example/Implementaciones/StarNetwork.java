package org.example.Implementaciones;

import org.example.NetworkTopology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class StarNetwork implements NetworkTopology {
    private Node hub;
    private List<Node> nodes;
    private ExecutorService executor;
    private ReentrantLock lock;
    private Condition allNodesReceived;
    private boolean shutdownTriggered;

    @Override
    public void configureNetwork(int numberOfNodes) {
        hub = new Node(0);
        nodes = new ArrayList<>();
        for (int i = 1; i <= numberOfNodes; i++) {
            nodes.add(new Node(i));
        }
        executor = Executors.newFixedThreadPool(numberOfNodes + 1);
        lock = new ReentrantLock();
        allNodesReceived = lock.newCondition();
        shutdownTriggered = false;
    }

    @Override
    public void sendMessage(int from, int to, String message) {
        if (from < 0 || from >= nodes.size()) {
            throw new IllegalArgumentException("El nodo emision no existe, el cual es: " + from);
        }
        if (to < 0 || to >= nodes.size()) {
            throw new IllegalArgumentException("El nodo receptor no existe, el cual es: " + to);
        }
        lock.lock();
        try {
            executor.submit(() -> {
                if (from == 0) {
                    hub.sendMessage(message, to, 1);
                } else {
                    nodes.get(from - 1).sendMessage(message, to, 1);
                }
            });
        } finally {
            lock.unlock();
        }
    }

    private int calculateLatency() {
        return (int) (Math.random() * 1000);
    }

    @Override
    public void runNetwork() {
        executor.submit(hub::run);
        for (Node node : nodes) {
            executor.submit(node::run);
        }
    }

    @Override
    public void shutdown() {
        lock.lock();
        try {
            while (!shutdownTriggered) {
                allNodesReceived.await();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("El servicio de ejecutores no termino");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private class Node {
        private final int nodeNumber;

        public Node(int nodeNumber) {
            this.nodeNumber = nodeNumber;
        }

        public void run() {
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void sendMessage(String message, int to, int hopCount) {
            try {
                Thread.sleep(calculateLatency());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Nodo " + nodeNumber + " recibio el mensaje: " + message + " en el salto " + hopCount);
            lock.lock();
            try {
                if (nodeNumber == to) {
                    shutdownTriggered = true;
                    allNodesReceived.signalAll();
                } else if (nodeNumber == 0) { // Hub node
                    executor.submit(() -> nodes.get(to - 1).sendMessage(message, to, hopCount + 1));
                }
            } finally {
                lock.unlock();
            }
        }
    }
}