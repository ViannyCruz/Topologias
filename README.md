# Topologias
## Estructura del marco de trabajo
El proyecto sigue un patrón modular y de interfaz que facilita la gestión y simulación de diferentes topologías de red.

Estructura General
1. Paquete Principal (org.example):
NetworkManager: Clase que gestiona diferentes topologías de red. Utiliza un Map para almacenar instancias de diferentes topologías y proporciona métodos para configurar, ejecutar, enviar mensajes y apagar las redes.
NetworkTopology: Interfaz que define los métodos básicos que cualquier topología de red debe implementar (configureNetwork, sendMessage, runNetwork, shutdown).
Message: Clase simple que representa un mensaje con un remitente, un destinatario y contenido.

2. Paquete de Implementaciones (org.example.Implementaciones):
Clases de Topologías Específicas: Clases como BusNetwork, RingNetwork, StarNetwork, etc., que implementan la interfaz NetworkTopology y proporcionan la lógica específica para cada tipo de red.


### Uso del Marco de Trabajo
1. NetworkManager
Instanciación:

NetworkManager manager = new NetworkManager();

Configuración y Ejecución de una Red:
manager.runNetwork("Bus", 5);  // Configura y corre una red tipo Bus con 5 nodos.

Envío de Mensajes:
manager.sendMessage("Bus", 0, 3, "Hola Nodo 3");  // Envía un mensaje desde el nodo 0 al nodo 3 en la red Bus.

Apagado de la Red:
manager.shutdownNetwork("Bus");  // Apaga la red Bus.


El marco de trabajo ofrece una estructura flexible y modular para simular diversas topologías de red. Las clases y métodos están diseñados para permitir la fácil incorporación de nuevas topologías, manteniendo una interfaz común que facilita la administración y ejecución de las redes.
