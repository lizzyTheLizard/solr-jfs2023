# Infrastruktur
* Zeigen [docker-compose.yml](docker-compose.yml)
* Starten Infrastruktur `docker-compose up`
* Zugriff UI http://localhost:8983/solr

# Java-Code
* Zeigen Frontend [game.html](src/main/resources/templates/game.html)
* Zeigen Controller 
[GameController.java](src/main/java/site/gutschi/solrexample/transport/GameController.java) und
[InitializerController.java](src/main/java/site/gutschi/solrexample/transport/InitializerController.java)
* Initialisieren http://localhost:8080/api/initialize
* Öffnen UI http://localhost:8080


# SolrJ
* In [pom.xml](pom.xml), danach neu starten

```
		<dependency>
			<groupId>org.apache.solr</groupId>
			<artifactId>solr-solrj</artifactId>
			<version>9.2.0</version>
		</dependency>
```

# Initialisieren
* Danach suchen in  http://localhost:8983/solr
* In [InitializerController.java](src/main/java/site/gutschi/solrexample/transport/InitializerController.java)
```

    public void initIndex(Collection<Game> games) {
        try (SolrClient solr = new Http2SolrClient.Builder("http://localhost:8983/solr/games").build()){
            solr.deleteByQuery("*.*");
            games.forEach(game -> {
                try {
                    SolrInputDocument document = convertToSolr(game);
                    solr.add(document);
                } catch (SolrServerException | IOException e) {
                    log.warn("Could not index " + game, e);
                }
            });
            solr.commit();
        } catch (IOException | SolrServerException e) {
            log.warn("Could not re-index ",e);
        }
    }


    private SolrInputDocument convertToSolr(Game game) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", game.getId());
        document.addField("title", game.getTitle());
        if (game.getReleaseDate() != null) {
            final var dateStr = game.getReleaseDate()
                    .format(DateTimeFormatter.ISO_DATE)
                    + "T00:00:00Z";
            document.addField("releaseDate", dateStr);
        }
        game.getTeam().forEach(t -> document.addField("team", t));
        game.getGenres().forEach(g -> document.addField("genre", g));
        document.addField("summary", game.getSummary());
        game.getReviews().forEach(r -> document.addField("review", r));
        if (game.getRating() != null) {
            document.addField("rating", game.getRating());
        }
        document.addField("timesListed", game.getTimesListed());
        document.addField("wishlist", game.getWishlist());
        document.addField("backlogs", game.getBacklogs());
        document.addField("playing", game.getPlaying());
        document.addField("plays", game.getPlays());
        document.addField("numberOfReviews", game.getNumberOfReviews());
        return document;
    }
```





# Suchen
* In [GameController.java](src/main/java/site/gutschi/solrexample/transport/GameController.java) und
```
        try (SolrClient solr = new Http2SolrClient.Builder("http://localhost:8983/solr/games").build()){
            final var solrQuery = new SolrQuery();
            solrQuery.set("q", search);
            solrQuery.set("fl", "id");
            solrQuery.setRows(1000);
            final var response = solr.query(solrQuery);
            final var ids = response.getResults().stream()
                    .map(s -> (String) s.getFieldValue("id"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            log.info("Get " + ids.size() + " games");
            return gameRepository.findAllById(ids);
        } catch (IOException | SolrServerException e) {
            log.error("Could not search: " + search, e);
            return List.of();
        }
```



