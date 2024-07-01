package org.example;

public class Main {
    public static void main(String[] args) {
        NetworkManager manager = new NetworkManager();
        int numberOfNodes = 4;

        manager.runNetwork("Bus", numberOfNodes);
        manager.sendMessage("Bus", 0, 2, "Hellooo Bus!");
        manager.shutdownNetwork("Bus");

        /*
        manager.runNetwork("Ring", numberOfNodes);
        manager.sendMessage("Ring", 0, 2, "Hellooo Ring!");
        manager.shutdownNetwork("Ring");

        manager.runNetwork("Star", numberOfNodes);
        manager.sendMessage("Star", 0, 2, "Hellooo Star!");
        manager.shutdownNetwork("Star");

        manager.runNetwork("Tree", numberOfNodes);
        manager.sendMessage("Tree", 0, 2, "Hellooo Tree!");
        manager.shutdownNetwork("Tree");

        manager.runNetwork("FCN", numberOfNodes);
        manager.sendMessage("FCN", 0, 2, "Hellooo FCN!");
        manager.shutdownNetwork("FCN");

        manager.runNetwork("Swt", numberOfNodes);
        manager.sendMessage("Swt", 0, 2, "Hellooo Swt!");
        manager.shutdownNetwork("Swt");

        manager.runNetwork("Mesh", numberOfNodes);
        manager.sendMessage("Mesh", 0, 2, "Hellooo Mesh!");
        manager.shutdownNetwork("Mesh");

        manager.runNetwork("Hyper", numberOfNodes);
        manager.sendMessage("Hyper", 0, 2, "Hellooo Hyper!");
        manager.shutdownNetwork("Hyper");*/
    }
}
