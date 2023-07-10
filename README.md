# solr-jfs2023
Folien, Code-Beispiele etc. für den Vortag am Java Forum Stuttgart 2023

# Folien
Eine aktuelle Version der Folien findet ihr unter [Folien.pdf](folien.pdf)

# Beispiel
In diesem Repo findet ihr ein SpringBoot Beispielprojekt. Es kann mittels
```
./mvnw spring-boot:run
```

gestartet werden. Die notwendige Infrastruktur (Solr, Datenbank) kann mittels docker compose gestartet werden
```
docker-compose up
```

Das Example bietet eine UI, auf die unter http://localhost:8080 zugegriffen werden kann. Die Applikation implementiert eine Datenbank von Computer-Games inkl. Hersteller-Studio, Gerne und Bewertungen.
Beim ersten starten muss die Datenbank mit den Daten aus [games.csv](src/main/resources/games.csv) initialisiert werden. Dies kann mit der URL 
In Branch 'start', die Suchfunktion ist noch nicht implemeniert


# Links
