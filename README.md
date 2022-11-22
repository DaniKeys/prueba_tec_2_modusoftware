## Proyecto Libreria
**PRUEBA TECNICA 2 MODUSOFTWARE**

**Proyecto Libreria** Es una API REST de consultas de informacion sobre libros, filtrando por ciertos parametros, operaciones CRUD con cambios a la persistencia, arquitectura multicapa, servicios de consumo atraves del cliente postman, framework spring, lenguaje java

### **Instrucciones de uso**

+ Cree la base de datos en su gestor de preferencia , los archivos dados en el folder `schema_db_script`. en el empaquetamiento del proyecto
> NOTA: La conexion esta establecida con los valores dados en los scripts 


**Informacion de conexion archivo properties**
~~~
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/test
spring.datasource.username=test-user
spring.datasource.password=test12345
~~~
**Consumo de la API EN Postman**
Antes de consumir la APi levante su APi en el `IDE` para establecer conexion con su cliente, Inserte la siguiente `URL` base en su cliente 

~~~
http://127.0.0.1:9092/
~~~
>NOTA Tenga presente el puerto de conexion ya establecido en el properties


## **Metodos y EndPoints**

Ejemplos de Funcionamiento de consultas por **`PATHPARAM`** y **`REQUESTBODY`** tener presente el respectivo `METODO` Y `URL`

![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/getAllRequest.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/getAllBookNameRequest.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/CreateBookRequest.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/UpdateStockRequest.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/BuyBookRequest.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img//getAllVentasRequest.png)


## **Pruebas en POSTMAN**

En el folder `postman_script_testing` encontrara el script `.json` con los test de las consultas, ejecute RunCollection en las opciones de la coleccion, selccione y ejecute todos los metodos

![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/runCollection.png)
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/runAllMethod.png)
>Tambien encontrara un script `.json` con el resultado de los test

## Se testeo las consultas con algunas de las aserciones mas comunes de postman

+ Validacion de status
+ Tiempo de respuesta de la solicitud
+ Validar formato del ContenType en el request o response segun cada metodo de consulta
+ Validar la presencia del ContenType en el request o response segun cada metodo de consula
+ Tipo de datod de propiedades
+ Validar que propiedades retorna segun requerimientos de cada metodo de consulta


## **Documentacion Swagger**

En el folder `doc_swagger` encontrara el script .`yaml` de la documentacion, Para solo lectura copie este script en el editor de Swagger https://editor.swagger.io/
si quiere ejecutar pruebas de la API REST desde el servidor swagger, despues de levantar la Api vaya a su navegador y pegue la siguiente url http://localhost:9092/swagger-ui/index.html

>NOTA No altere el valor de dominio como 'localhost'ni lo reemplaze por 127.0.0.1 ya que en la configuración del proyecto esta establecida la anterior URL 
![](https://github.com/DaniKeys/prueba_tec_2_modusoftware/blob/main/prueba_tecnica_2/img/image.png)
## Tecnologias y Componentes
>STS,MySql,Postman,SpringBoot,SpringDataJPA,Junit,lombok,Swagger,SpringFox,MapStruct,formatos,json,yaml,js
