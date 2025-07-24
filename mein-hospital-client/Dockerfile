# Erster Schritt: Baue die React-Anwendung
FROM node:16 AS build

# Setzen des Arbeitsverzeichnisses im Docker-Image
WORKDIR /app

# Kopiere die package.json und package-lock.json (oder yarn.lock) Dateien
COPY package*.json ./

# Installiere Abhängigkeiten
RUN npm install

# Kopiere den Rest der Anwendungsdateien
COPY . . 

# Baue die Anwendung
RUN npm run build

# Zweiter Schritt: Setze die gebaute App auf den Nginx-Server
FROM nginx:stable-alpine

# Kopiere die gebauten statischen Dateien in den Nginx-Server
COPY --from=build /app/build /usr/share/nginx/html

# Exponiere Port 80
EXPOSE 80

# Starte Nginx im Vordergrund
CMD ["nginx", "-g", "daemon off;"]
