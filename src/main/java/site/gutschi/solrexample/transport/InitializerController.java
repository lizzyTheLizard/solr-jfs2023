package site.gutschi.solrexample.transport;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.gutschi.solrexample.input.CsvInputReader;
import site.gutschi.solrexample.model.Game;
import site.gutschi.solrexample.model.GameRepository;

import java.util.Collection;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/initialize")
public class InitializerController {
    private final GameRepository gameRepository;
    private final CsvInputReader csvInputReader;

    @GetMapping("")
    @Transactional
    public void init() {
        final var games = csvInputReader.readCsv();
        initDB(games);
    }

    private void initDB(Collection<Game> games){
        gameRepository.truncate();
        gameRepository.saveAll(games);

    }
}
