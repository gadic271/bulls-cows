package bulls.cows;

public class GuessResponse {
    private int bulls;
    private int cows;
    private boolean gameOver;
    private String message;

    public GuessResponse(int bulls, int cows, boolean gameOver, String message) {
        this.bulls = bulls;
        this.cows = cows;
        this.gameOver = gameOver;
        this.message = message;
    }


}