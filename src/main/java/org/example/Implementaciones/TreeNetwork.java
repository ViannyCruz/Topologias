package org.example.Implementaciones;

import org.example.NetworkTopology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TreeNetwork implements NetworkTopology {
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
        for (int i = 0; i < numberOfNodes; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            if (leftChild < numberOfNodes) {
                nodes.get(i).addChild(nodes.get(leftChild));
            }
            if (rightChild < numberOfNodes) {
                nodes.get(i).addChild(nodes.get(rightChild));
            }
        }
        executor = Executors.newFixedThreadPool(numberOfNodes);
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
            executor.submit(() -> nodes.get(from).sendMessage(message, to, 1));
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
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }
    private class Node {
        private final int nodeNumber;
        private List<Node> children;

        public Node(int nodeNumber) {
            this.nodeNumber = nodeNumber;
            this.children = new ArrayList<>();
        }

        public void addChild(Node child) {
            this.children.add(child);
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
            if (nodeNumber == to) {
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
            } else {
                for (Node child : children) {
                    executor.submit(() -> child.sendMessage(message, to, hopCount + 1));
                }
            }
        }
    }
}