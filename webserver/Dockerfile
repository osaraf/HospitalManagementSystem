# Erster Schritt: Verwenden Sie ein Gradle-Basis-Image für den Build-Prozess
FROM gradle:7.4-jdk17-alpine AS build
  
  # Kopieren des gesamten Gradle-Projekts in das Docker-Image
COPY --chown=gradle:gradle . /home/gradle/src
  
  # Setzen des Arbeitsverzeichnisses
WORKDIR /home/gradle/src
  
  # Gradle-Build ausführen, um die JAR-Datei zu erstellen
RUN gradle build --no-daemon
  
  # Zweiter Schritt: Verwenden Sie ein schlankeres JRE-Image für die Laufzeit
FROM openjdk:17.0.1-jdk-slim
  
  # Setzen des Arbeitsverzeichnisses im finalen Image
WORKDIR /app
  
  # Kopieren der gebauten JAR-Datei aus dem Build-Stage
  # Ersetzen Sie 'hms-0.0.1-SNAPSHOT.jar' mit dem tatsächlichen Namen der JAR-Datei, wenn sie anders lautet
COPY --from=build /home/gradle/src/build/libs/webserver-0.0.1-SNAPSHOT.jar /app/webserver.jar
  
  # Standardkommando zum Ausführen der Anwendung
CMD ["java", "-jar", "/app/webserver.jar"]
