package bulls.cows;


import com.example.bullsandcows.model.GuessResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {
    private String secret;
    private boolean gameActive;

    public GameService() {
        newGame();
    }

    public void newGame() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= 9; i++) digits.add(i);
        Collections.shuffle(digits);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) sb.append(digits.get(i));
        secret = sb.toString();
        gameActive = true;
        System.out.println("New secret: " + secret); // для отладки
    }

    public GuessResponse checkGuess(String guess) {
        if (!gameActive) {
            return new GuessResponse(0, 0, true, "Игра окончена. Начните новую игру.");
        }

        int bulls = 0;
        int cows = 0;

        boolean[] secretUsed = new boolean[4];
        boolean[] guessUsed = new boolean[4];

        // быки
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bulls++;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // коровы
        for (int i = 0; i < 4; i++) {
            if (guessUsed[i]) continue;
            for (int j = 0; j < 4; j++) {
                if (secretUsed[j]) continue;
                if (guess.charAt(i) == secret.charAt(j)) {
                    cows++;
                    secretUsed[j] = true;
                    break;
                }
            }
        }

        boolean gameOver = (bulls == 4);
        String message = gameOver ? "Поздравляем! Вы угадали число!" : "Попробуйте ещё.";
        if (gameOver) gameActive = false;

        return new GuessResponse(bulls, cows, gameOver, message);
    }

    public String getSecret() {
        return secret;
    }
}