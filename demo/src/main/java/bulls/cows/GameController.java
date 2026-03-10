package bulls.cows;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:5500")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/guess")
    public GuessResponse makeGuess(@Valid @RequestBody GuessRequest request) {
        return gameService.checkGuess(request.getGuess());
    }

    @PostMapping("/new")
    public void newGame() {
        gameService.newGame();
    }

    @GetMapping("/secret")
    public String getSecret() {
        return gameService.getSecret();
    }
}