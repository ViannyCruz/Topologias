package org.example.Implementaciones;

import org.example.NetworkTopology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SwitchedNetwork implements NetworkTopology {
    private List<Node> nodes;
    private Switch networkSwitch;
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
        networkSwitch = new Switch();
        executor = Executors.newFixedThreadPool(numberOfNodes + 1); // +1 for the switch
        lock = new ReentrantLock();
        allNodesReceived = lock.newCondition();
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
            nodesReceivedCount = 0;
            System.out.println("Enviando el mensaje desde el nodo " + from + " hacia el nodo " + to + ": " + message);
            executor.submit(() -> nodes.get(from).sendMessage(message, to));
        } finally {
            lock.unlock();
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
        executor.submit(networkSwitch::run);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
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

        public void sendMessage(String message, int to) {
            try {
                Thread.sleep(calculateLatency());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Nodo " + nodeNumber + " esta enviando el mensaje al switcher: " + message);
            executor.submit(() -> networkSwitch.receiveMessage(message, nodeNumber, to));
        }

        public void receiveMessage(String message, int from) {
            try {
                Thread.sleep(calculateLatency());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Nodo " + nodeNumber + " mensaje recibio del nodo " + from + ": " + message);
            if (nodeNumber == from) {
                System.out.println("Nodo " + nodeNumber + " es el destino y mando un mensaje diferente: 'Confirmación del nodo " + nodeNumber + "'");
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

    private class Switch {
        public void run() {
        }

        public void receiveMessage(String message, int from, int to) {
            try {
                Thread.sleep(calculateLatency());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("El switcher recibio el mensaje del Nodo " + from + " hacia el Nodo " + to + ": " + message);
            executor.submit(() -> nodes.get(to).receiveMessage(message, from));
        }
    }
}