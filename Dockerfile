# Erster Schritt: Verwenden Sie ein Maven-Basis-Image für den Build-Prozess
FROM maven:3.8.1-openjdk-17-slim AS build

# Setzen des Arbeitsverzeichnisses im Build-Image
WORKDIR /app

# Kopieren des gesamten Maven-Projekts in das Docker-Image
COPY . /app

# Maven-Build ausführen, um die JAR-Datei zu erstellen
RUN mvn clean package

# Zweiter Schritt: Verwenden Sie ein schlankeres JRE-Image für die Laufzeit
FROM openjdk:17.0.1-jdk-slim

# Setzen des Arbeitsverzeichnisses im finalen Image
WORKDIR /app

# Kopieren der gebauten JAR-Datei aus dem Build-Stage
COPY --from=build /app/target/hmsMaven-1.0-SNAPSHOT.jar /app

# Umgebungsvariablen für Konfigurationsdateien und Applikationsargumente
ENV CONFIG_FILE=config.json
ENV HOSPITAL_INFO_FILE=hospitalInfos.json

# Standardkommando zum Ausführen der Anwendung
CMD java -jar hmsMaven-1.0-SNAPSHOT.jar $IS_FIRST_NODE $ACQUAINTANCE_HOST $ACQUAINTANCE_PORT
