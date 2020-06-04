# Android
Proyectos de práctica para el desarrollo de Android. 

## Proyectos ##

### Proyecto 4 ###
Para este proyecto se conecta a: https://jsonplaceholder.typicode.com/photos para descargar los albumes. 
La primera vez que se ejecuta la app, se conecta a esa pagina usando un **ASyncTaskLoader** para descargar todos los albumes, 
y los guarda en una base de datos, para que despues se use un **CursorLoader y un RecyclerView**
para mostrarlos, para usar el CursorLoader se necesito crear un **ContentProvider**.
Cuando el usuario da click en alguno de los albumes, se abre otra Actividad con los
detalles de este, e incluye una opción en su menu para poder borrar el album.
Las siguientes veces que el programa se ejecuta no tendrá que conectarse. Pero el programa incluye 
un menú con 3 opciones:
1. Refrescar: Al presionar esta opción, el programa se conecta a esa misma pagina web
para volver a descargar los albumes(sin duplicación).
2. Agregar: Esta opción permite agregar un nuevo álbum, se abre un **Dialog o un
Activity** donde el usuario ingresa los datos del álbum (y la opción de cancelar o aceptar).
3. Settings: Al seleccionar esta opción, el programa muestra un Activity para configurar los
**settings** (**Usando SettingsActivity, y su RecyclerView** debe escuchar a los cambios en los
settings). Los settings tienen 3 opciones: una lista para ver porque parámetro se quiere ordenados los datos, 
ya sea por id o por título. 

Además, tiene como caracteristicas:
1. Se actualiza automáticamente los álbumes cuando hay WiFI(una vez al día, usando el **JobScheduler**)
2. Se crea un segundo programa que checa si existe el **ContentProvider** que se creo y muestra el número de albumes actualmente.
3. Se permite la funcionalidad de jalar la lista hacia abajo para refrescar la información (como lo hace FB
o GMail).
4. Se puede agregar la imagen de un álbum a la galeria de Android usando el
**ContentProvider MediaStore**.

### Proyecto 3 ###
Se crea una aplicación que permite fijar *alarmas*, ya sea que el usuario las programe a una hora especifica, o que se active 
en cierta cantidad de tiempo (cuenta regresiva). Se considera que el usuario puede reiniciar su teléfono y la alarma debe seguir
funcionando.
Para las alarmas en cuenta regresiva, se usa una **notificación continua** mostrando el tiempo
(esta notificación no es eliminable por el usuario), esta tiene la opción de cancelar la alarma.
Las alarmas mandan una notificación cuando se activen.
En resumen se uso: **notificaciones, alarm manager, servicios y broadcasts**.

### Proyecto 2 ###
El objetivo de este proyecto es el uso de **ASyncTaskLoader, menus, Recycler View, conexiones web
y objetos JSON**, en adición del uso de Material Design y assets para modo portrait y landscape. Las actividades son:
1. Esta actividad tiene varias etapas, primero, se realiza una **petición web** a:
https://jsonplaceholder.typicode.com/photos, esta tiene 5000 elementos codificados en **formato
JSON**, por lo que se codifica esa información, esta llamada web esta dentro de un **Loader**. 
Ese loader se conecta con un **adaptador**, que usa en conjunto con un **RecyclerView** para mostrarlos en la actividad. 
La actividad muestra la imagen, albumId, la imagen. La actividad también contiene un **menú**, donde el usuario escoge 
como quiere los **elementos ordenados**; por id o por título. Al dar clic en alguno de los elementos, manda al usuario a una 
nueva actividad:
2. En esta actividad muestra la imagen, albumId, y la url, la cual manda el navegador en esa página web.

### Proyecto 1 ###
El proyecto consiste en una app con 3 actividades:
1. Esta pantalla muestra un **Spinner**, donde el usuario puede escoger de entre la lista de
cosas y un botón “CONTINUAR”, este botón esta deshabilitado si el usuario no ha seleccionado una opción (usando el
**onitemselectedlistener**), cuando el usuario selecciono una opción, este botón se habilita (si
después el usuario deselecciona su opción, vuelve a desactivarse), al presionarse, ese botón
los mandara a la actividad 2.
2. Esta pantalla tiene como **viewgroup raíz** un **scrollView**, dentro de este **scrollView**, hay una
descripción de la cosa seleccionada en la actividad 1(un lugar, un juego, un animal), esta
descripción incluye una imagen y un texto. Al final de la descripción hay dos botones, “SEND” y
“VER MAS”. “VER MAS” que abre una página de internet con información al respecto, esta página se abre usando 
un **implicit intent**. El botón “SEND” manda al usuario a la actividad 3.
3. Esta pantalla consiste en un campo de texto para que el usuario escriba un email (se usa el
**inputtype** requerido para campos de mail) y un botón “SEND”, este botón usa
**startActiviyForResult** y un **implicit intent** para mandar un correo electrónico, este implicit intent
incluye la dirección de correo que el usuario escribió en sus extras para que la aplicación de
mail rellene ese dato. En esta actividad se implementa **onActivityResult** para saber cuándo el
usuario regreso de enviar el correo. Y en esa función se muestra un mensaje al usuario
agradeciéndole por enviar el correo.
