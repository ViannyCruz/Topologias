package org.example.Implementaciones;
import org.example.NetworkTopology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BusNetwork implements NetworkTopology {
    private List<Node> nodes;
    private ExecutorService executor;
    private ReentrantLock lock;
    private Condition allNodesReceived;
    private int nodesReceivedCount;

    @Override
    public void configureNetwork(int numberOfNodes) {
        nodes = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new Node(i));
        }
        executor = Executors.newFixedThreadPool(numberOfNodes);
        lock = new ReentrantLock();
        allNodesReceived = lock.newCondition();
    }

    @Override
    public void sendMessage(int from, int to, String message) {
        if (to >= 0 && to < nodes.size()) {
            nodesReceivedCount = 0;
            for (Node node : nodes) {
                executor.submit(() -> node.receiveMessage(message, calculateLatency()));
            }
        } else {
            throw new IllegalArgumentException("El indice del nodo no es valido " + to);
        }
    }

    private int calculateLatency() {
        return (int) (Math.random() * 1000);
    }

    @Override
    public void runNetwork() {
        for (Node node : nodes) {
            executor.submit(node::run);
        }
    }

    @Override
    public void shutdown() {
        lock.lock();
        try {
            while (nodesReceivedCount < nodes.size()) {
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
                    System.err.println("El grupo de ejecutores no finalizo");
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

        public void receiveMessage(String message, int latency) {
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Nodo " + nodeNumber + " recibio el mensaje: " + message);
            lock.lock();
            try {
                nodesReceivedCount++;
                if (nodesReceivedCount == nodes.size()) {
                    allNodesReceived.signalAll();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}