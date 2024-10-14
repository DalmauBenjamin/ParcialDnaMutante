# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo build.gradle y el script gradlew
COPY build.gradle ./
COPY gradlew ./
RUN chmod +x gradlew

# Copia el código fuente
COPY src ./src

# Construye el proyecto
RUN ./gradlew build --no-daemon

# Copia el archivo JAR generado al contenedor (ajusta el nombre según tu configuración)
COPY build/libs/mutantes-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
