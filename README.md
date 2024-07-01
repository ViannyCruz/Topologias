# Practica #3 - Topologías de Redes para Procesamiento Paralelo
## Estructura del marco de trabajo
El proyecto sigue un patrón modular y de interfaz que facilita la gestión y simulación de diferentes topologías de red.

### Estructura General
1. Paquete Principal (org.example):
NetworkManager: Clase que gestiona diferentes topologías de red. Utiliza un Map para almacenar instancias de diferentes topologías y proporciona métodos para configurar, ejecutar, enviar mensajes y apagar las redes.
NetworkTopology: Interfaz que define los métodos básicos que cualquier topología de red debe implementar (configureNetwork, sendMessage, runNetwork, shutdown).
Message: Clase simple que representa un mensaje con un remitente, un destinatario y contenido.

2. Paquete de Implementaciones (org.example.Implementaciones):
Clases de Topologías Específicas: Clases como BusNetwork, RingNetwork, StarNetwork, etc., que implementan la interfaz NetworkTopology y proporcionan la lógica específica para cada tipo de red.


### Uso del Marco de Trabajo
NetworkManager
Instanciación:

NetworkManager manager = new NetworkManager();

Configuración y Ejecución de una Red:
manager.runNetwork("Bus", 5);  // Configura y corre una red tipo Bus con 5 nodos.

Envío de Mensajes:
manager.sendMessage("Bus", 0, 3, "Hola Nodo 3");  // Envía un mensaje desde el nodo 0 al nodo 3 en la red Bus.

Apagado de la Red:
manager.shutdownNetwork("Bus");  // Apaga la red Bus.


El marco de trabajo ofrece una estructura flexible y modular para simular diversas topologías de red. Las clases y métodos están diseñados para permitir la fácil incorporación de nuevas topologías, manteniendo una interfaz común que facilita la administración y ejecución de las redes.


### Ejemplos de código

- Bus Network
manager.runNetwork("Bus", numberOfNodes);
manager.sendMessage("Bus", 0, 2, "Hellooo Bus!");
manager.shutdownNetwork("Bus");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/a6da9b0e-e2b0-4f80-bfcb-6a8996a2ba20)


- Ring Network
manager.runNetwork("Ring", numberOfNodes);
manager.sendMessage("Ring", 0, 2, "Hellooo Ring!");
manager.shutdownNetwork("Ring");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/3a800290-6386-47a5-84fc-1324623732a5)


- Mesh Network 
manager.runNetwork("Mesh", numberOfNodes);
manager.sendMessage("Mesh", 0, 2, "Hellooo Mesh!");
manager.shutdownNetwork("Mesh");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/c49c4cc1-6edd-4aee-a374-19432d42e9c4)


- Star Network 
manager.runNetwork("Star", numberOfNodes);
manager.sendMessage("Star", 0, 2, "Hellooo Star!");
manager.shutdownNetwork("Star");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/00635f75-894a-4b41-b606-a0d5a4e2acb0)


- Hypercube Network 
manager.runNetwork("Hyper", numberOfNodes);
manager.sendMessage("Hyper", 0, 2, "Hellooo Hyper!");
manager.shutdownNetwork("Hyper");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/3a519c09-a44a-4494-89ab-ba4dc017e976)


- Tree Network 
manager.runNetwork("Tree", numberOfNodes);
manager.sendMessage("Tree", 0, 2, "Hellooo Tree!");
manager.shutdownNetwork("Tree");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/42f89720-a29f-4dd7-b7f8-5c7d4f14210c)


- Fully Connected Network 
manager.runNetwork("FCN", numberOfNodes);
manager.sendMessage("FCN", 0, 2, "Hellooo FCN!");
manager.shutdownNetwork("FCN");

Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/ab3eaaa4-9d2a-4a76-9963-26ec0d2247a5)


- Switched Network
manager.runNetwork("Swt", numberOfNodes);
manager.sendMessage("Swt", 0, 2, "Hellooo Swt!");
manager.shutdownNetwork("Swt");



Salida:
![image](https://github.com/ViannyCruz/Topologias/assets/113074158/132099ee-7484-49d0-9976-2bfb1d876423)




## Uso del Marco de Trabajo

### Instalar y Configurar

1. Clona el repositorio en tu máquina local:
    ```sh
    git clone [https://github.com/tu-usuario/network-simulation-framework.git
    cd network-simulation-framework](https://github.com/ViannyCruz/Topologias.git)
    ```

2. Asegúrate de tener configurado tu entorno de desarrollo Java.

### Ejecutar el Proyecto

Para ejecutar el proyecto, puedes crear una instancia de `NetworkManager` y usar sus métodos para configurar y ejecutar una red.

#### Ejemplo de Uso

```java
import org.example.NetworkManager;

public class Main {
    public static void main(String[] args) {
        NetworkManager manager = new NetworkManager();

        // Configurar y correr una red tipo Bus con 5 nodos
        manager.runNetwork("Bus", 5);

        // Enviar un mensaje desde el nodo 0 al nodo 3 en la red Bus
        manager.sendMessage("Bus", 0, 3, "Hola Nodo 3");

        // Apagar la red Bus
        manager.shutdownNetwork("Bus");
    }
}
