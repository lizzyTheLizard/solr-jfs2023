package site.gutschi.solrexample.transport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import site.gutschi.solrexample.model.GameRepository;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameRepository gameRepository;

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/games/{id}")
    public String showGame(@PathVariable("id") int id, Model model) {
        log.info("Get game " + id);
        final var game = gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("game", game);
        return "game";
    }

    @GetMapping("/")
    public RedirectView redirectRoot() {
        return new RedirectView("/games");
    }

    @GetMapping("/games/")
    public RedirectView redirectSlash() {
        return new RedirectView("/games");
    }

    @SuppressWarnings("SameReturnValue")
    @GetMapping("/games")
    public String showGames(Model model) {
        final var games = gameRepository.findAll();
        model.addAttribute("games", games);
        return "games";
    }
}
