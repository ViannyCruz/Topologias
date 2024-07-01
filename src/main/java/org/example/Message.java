package org.example;

public class Message {
    private int from;
    private int to;
    private String content;

    public Message(int from, int to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getContent() {
        return content;
    }
}