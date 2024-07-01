package org.example;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Node implements Runnable {
    private int id;
    private BlockingQueue<String> messageQueue;

    public Node(int id) {
        this.id = id;
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    public int getId() {
        return id;
    }

    public void receiveMessage(String message) {
        System.out.println("Nodo " + id + " mensaje recibido: " + message);
        messageQueue.offer(message);
    }

    @Override
    public void run() {
        System.out.println("Nodo " + id + " esta corriendo.");
        try {
            while (true) {
                String message = messageQueue.take();
                System.out.println("Nodo " + id + " a procesado mensaje: " + message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}