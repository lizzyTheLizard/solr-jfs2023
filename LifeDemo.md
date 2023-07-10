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

# Initialisieren
```
    public void reindex(Collection<Game> games) {
        try {
            try {
                solr.deleteByQuery("*.*");
            } catch (Exception e) {
                throw SolrCommunicationException.couldNotDeleteDocuments(e);
            }
            games.forEach(game -> {
                SolrInputDocument document = convertToSolr(game);
                try {
                    solr.add(document);
                } catch (Exception e) {
                    throw SolrCommunicationException.couldNotAddGame(game, e);
                }
            });
            log.debug("Commit changes");
            solr.commit();
        } catch (Exception e) {
            rollbackAndFail(e);
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
        return document
    }
```


