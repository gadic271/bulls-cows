package bulls.cows;

import com.example.bullsandcows.model.GuessRequest;
import com.example.bullsandcows.model.GuessResponse;
import com.example.bullsandcows.service.GameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:8080") // для разработки
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