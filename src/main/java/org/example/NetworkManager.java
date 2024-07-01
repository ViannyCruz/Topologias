package org.example;

import org.example.Implementaciones.BusNetwork;
import org.example.Implementaciones.RingNetwork;
import org.example.Implementaciones.StarNetwork;
import org.example.Implementaciones.TreeNetwork;
import org.example.Implementaciones.FullyConnectedNetwork;
import org.example.Implementaciones.SwitchedNetwork;
import org.example.Implementaciones.MeshNetwork;
import org.example.Implementaciones.HypercubeNetwork;


import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private Map<String, NetworkTopology> networkTopologies;

    public NetworkManager() {
        networkTopologies = new HashMap<>();
        networkTopologies.put("Bus", new BusNetwork());
        networkTopologies.put("Ring", new RingNetwork());
        networkTopologies.put("Star", new StarNetwork());
        networkTopologies.put("Tree", new TreeNetwork());
        networkTopologies.put("FCN", new FullyConnectedNetwork());
        networkTopologies.put("Swt", new SwitchedNetwork());
        networkTopologies.put("Mesh", new MeshNetwork());
        networkTopologies.put("Hyper", new HypercubeNetwork());
    }

    public void runNetwork(String topologyType, int numberOfNodes) {
        NetworkTopology topology = networkTopologies.get(topologyType);
        if (topology != null) {
            System.out.println("Configurando " + topologyType + " network con " + numberOfNodes + " nodos.");
            topology.configureNetwork(numberOfNodes);
            System.out.println("Corriendo " + topologyType + " network.");
            topology.runNetwork();
        } else {
            System.out.println("Topologia " + topologyType + "desconocida");
        }
    }

    public void sendMessage(String topologyType, int from, int to, String message) {
        NetworkTopology topology = networkTopologies.get(topologyType);
        if (topology != null) {
            System.out.println("Enviando mensaje del Nodo " + from + " hacia el Nodo " + to + ": " + message);
            topology.sendMessage(from, to, message);
        } else {
            System.out.println("Topologia " + topologyType + "desconocida");
        }
    }

    public void shutdownNetwork(String topologyType) {
        NetworkTopology topology = networkTopologies.get(topologyType);
        if (topology != null) {
            System.out.println("Apagando " + topologyType + " network.");
            topology.shutdown();
        } else {
            System.out.println("Topologia " + topologyType + "desconocida");
        }
    }
}