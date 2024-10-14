# Proyecto Detección de ADN Mutante

Este proyecto implementa una API REST que permite detectar si un humano es mutante basándose en una secuencia de ADN. Utiliza Java con Spring Boot y una base de datos H2 para almacenar las secuencias 

## Desafíos Completados

### Nivel 1:
- Se implementó un programa en Java que cumple con el método para detectar secuencias de ADN mutante

### Nivel 2:
- Se creó una API REST que expone el servicio `/mutant/` para detectar si un humano es mutante enviando una secuencia de ADN a través de un HTTP POST. El JSON enviado debe tener el siguiente formato:

```json
POST /mutant/
{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}

-La API devuelve un HTTP 200 OK si se verifica que es un mutante, o un HTTP 403 Forbidden en caso contrario