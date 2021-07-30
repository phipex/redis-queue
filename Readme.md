# Proxy Traefik

## Proxy de un servicio con traefik

Configuracion basica de un proxy a un servicio que se espera replicar, para el ejercicio se crea un servicio rest con spring-boot que retorna la ip actual del servicio

## Características

- Contiene dashboard en modo inseguro
- Nivel de log en debug

## Comandos basicos
Se compila la imagen del servicio con el siguiente comando

````
./gradlew build jibDockerBuild  
````
 Se corre el docker compose con el siguiente comando:
 ````
docker-compose up -d
````
Para correr varias replicas del mismo servicio:
````
docker-compose up -d --scale webservice=2
````
# Urls
- Dashboard
  http://localhost:8080/dashboard
  
- Whoami
  http://whoami.docker.localhost/
  
- Webservice
  http://webservice.docker.localhost/greeting


#  Pruebas de rendimiento y de carga

La pruebas de carga se realizan mediante [Locust](https://docs.locust.io/en/stable/what-is-locust.html)

## Rendimiento Por tiempo

Correr por tiempo en especifico (`-run-time 60s`) una prueba (`-f locust/basic.py -H 'http://webservice.docker.localhost'`) con determinado numero de usuarios (`-u 2`) y a diferente tasa (`-r 2`), esto para determinar cuantas peticiones puede soportar en un determinado tiempo. 

````
locust -f locust/basic.py -H 'http://webservice.docker.localhost' --headless -u 2 -r 2 --run-time 60s
````

## Rendimiento por cantidad

Correr determinada prueba para indentificar cuanto se demora el sistema en recibir determinada cantidad de peticiones

````
locust -f limit_reques.py -H 'http://webservice.docker.localhost'
````