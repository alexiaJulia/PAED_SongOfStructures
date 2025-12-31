# PAED_SongOfStructures
Programació Avançada i Estructura de Dades
Projecte del Segon Semestre - Estructures No Lineals
Song of Structures

Autores: Alexia Julià, Elia Yubero , Pere Grau y Elena Balfagón

-- Requisitos

Este proyecto ha sido desarrollado en Java utilizando el entorno de desarrollo IntelliJ IDEA 2024.3. 
Se ha utilizado el SDK 23 para su ejecución.

El programa ofrece una interfaz basada en menús para interactuar con diferentes algoritmos y estructuras de datos implementados.


-- Contenido del Proyecto

El proyecto está organizado por carpetas y clases según su funcionalidad:

- Algoritmos y Grafos -

BFS, DFS, Dijkstra, Kruskal: Algoritmos clásicos de grafos.
QuickSort: Algoritmo de ordenación rápida.
Vertex, Edge, Graf: Componentes de un grafo.
ReadFromFile: Lectura de grafos desde fichero.

- Tree 
BinaryTreeAVL: Implementa un árbol binario de búsqueda auto-balanceado (AVL).*
BinaryTreeVisualier: Herramienta gráfica para visualizar árboles binarios.*
Node: Clase que representa un héroe, conforman los nodos del árbol.
ReadFromFileTree: Lectura del dataset de Trees.
SpecialSends: Implementa algorismo de selección y deselección de héroes en el árbol binario dado.
Tree:implementa un árbol binario de búsqueda.Implementa funcionalidades de búsqueda, inserción, eliminación, etc.

*Opcionalmente se puede visualizar en dos dimensiones, mediante Swing de Java, el árbol binario sin balancear o balanceado de los Datasets XS y XSS.

- RTree (Árboles R) -

Implementación de una estructura de árbol R y su visualización:

RTree: Clase principal del árbol R.
Rectangle, NodeR, Figure: Representación de nodos y figuras geométricas.
ReadFromFileRTree: Lectura de datos para el árbol R.
EliminarRTree: Eliminación de elementos del árbol.
RTreeVisualizer: Visualización gráfica del árbol R. Si se clica en un punto o cuadrado, se puede ver la información del jugador.

- Tablas Hash -

Clases para gestionar y visualizar tablas:

Table: Representación.
TableVisualizer: representacion grafica del histograma.
ReadFromFileTables: Lectura de tablas desde archivos.
Production, Prueba: Casos de prueba o demostración.
Type: Tipos asociados a las tablas.

- Otros Clases -

Climate, Faccion: Clases relacionadas con información climática o categorización.
Tree: Contenido adicional no visible en la imagen.


- Controller -

El proyecto contiene:

Un controller general: Controller
Controladores específicos para cada clase, por ejemplo:
  ControllerGraph para gestionar grafos
  ControllerRTree para gestionar RTrees
  ControllerTables para gestionar Tablas Hash
  ControllerTree para gestionar Arboles

Estos controladores gestionan la lógica de cada componente y actúan como puente con la interfaz.


-- Ejecución del Programa

Para compilar y ejecutar correctamente el programa, es necesario iniciar desde la clase Main.
Al ejecutarla, se mostrará un primer menú donde el usuario podrá seleccionar la estructura de datos con la que desea trabajar.
A continuación, se desplegará un segundo menú para escoger el tamaño de los ficheros de entrada.**
Es importante destacar que en cada clase ReadFromFile de cada módulo se encuentra una parte de la ruta de los ficheros de los datasets. Al escoger un dataset desde el menú, esta ruta se completará automáticamente con el final correspondiente.
Por lo tanto, solo es compatible con ficheros que sigan la estructura de los datasets proporcionados y cuyo nombre termine en un tamaño entre "XXS" y "XXL" seguido de la extensión ".paed".
Si se requiere utilizar otro fichero con una ruta distinta, será necesario modificarla manualmente en la clase ReadFromFile, tanto en la cadena de texto predeterminada como en la parte que añade el final de la ruta.

Por último, aparecerá un tercer menú donde se podrá seleccionar la funcionalidad específica que se desea ejecutar.

**Se han dejado las carpetas donde añadir los datasets correspondientes para que sean compatibles con la ruta. Solo es añadir los ficheros de cada apartado en su carpeta correspondiente.

-- Notas Adicionales

Se han añadido **comentarios explicativos** en el código para facilitar su comprensión.
El programa permite ejecutar múltiples pruebas hasta que el usuario decida finalizar.
Es ideal para la práctica de estructuras y algoritmos clásicos como árboles, grafos y ordenaciones.
