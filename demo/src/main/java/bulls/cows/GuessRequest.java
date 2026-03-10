package bulls.cows;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class GuessRequest {
    @Size(min = 4, max = 4, message = "Длина должна быть 4 символа")
    @Pattern(regexp = "\\d{4}", message = "Только цифры")
    private String guess;

    public String getGuess() { return guess; }
    public void setGuess(String guess) { this.guess = guess; }
}