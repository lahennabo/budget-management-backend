# Étape 1 : Build de l'application avec Java 25 et Maven
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app

# Copier les fichiers du wrapper Maven et le pom.xml
COPY .mvn/ .mvn
COPY mvnw mvnw.cmd pom.xml ./
RUN chmod +x mvnw

# Télécharger les dépendances pour mettre en cache
RUN ./mvnw dependency:go-offline -B

# Copier les sources et compiler l'application
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécution de l'application avec un JRE Java 25 léger
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copier le fichier .jar généré depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Configurer le port d'écoute standard pour le Cloud
EXPOSE 8080

# Commande de démarrage de Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
