import java.util.Scanner;
import java.util.Random;

public class GuessTheNumber {
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    static int score = 0;

    public static void main(String[] args) {
        System.out.println("🎮 Welcome to the Number Guessing Game!");

        boolean playAgain = true;
        while (playAgain) {
            playRound();
            System.out.print("Do you want to play another round? (yes/no): ");
            String response = sc.next().toLowerCase();
            playAgain = response.equals("yes");
        }

        System.out.println("🎯 Game Over! Your Total Score: " + score);
    }

    public static void playRound() {
        int randomNumber = rand.nextInt(100) + 1;
        int maxAttempts = 7;
        int attempts = 0;

        System.out.println("\nGuess a number between 1 and 100. You have " + maxAttempts + " attempts.");

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int userGuess = getValidInput();

            attempts++;

            if (userGuess == randomNumber) {
                System.out.println("🎉 Correct! You guessed the number in " + attempts + " attempts.");

                int points = calculatePoints(attempts);
                score += points;
                System.out.println("You earned " + points + " points. Total Score: " + score);
                return;
            } else if (userGuess < randomNumber) {
                System.out.println("📉 Too low!");
            } else {
                System.out.println("📈 Too high!");
            }
        }

        System.out.println("❌ You've used all attempts! The correct number was: " + randomNumber);
    }

    public static int getValidInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    public static int calculatePoints(int attempts) {
        // Scoring: more points for fewer attempts
        switch (attempts) {
            case 1: return 100;
            case 2: return 90;
            case 3: return 80;
            case 4: return 70;
            case 5: return 60;
            case 6: return 50;
            case 7: return 40;
            default: return 0;
        }
    }
}
