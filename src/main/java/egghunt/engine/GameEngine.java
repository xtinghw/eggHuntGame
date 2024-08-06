package egghunt.engine;

import java.util.Random;
import java.util.Scanner;

public class GameEngine {
    private static int difficultyLevel;
    private char[][] map;
    private int[] entranceCell;
    private int[] exitCell;
    private int eggCounter;
    private int ownedEgg;
    private int lockCounter;
    private int keyCounter;
    private int ownedKey;
    private int stepCounter;
    private final int timeLimit = 100;
    private boolean gameWon;

    public void initializeGame(int difficultyLevel) {
        setDifficultyLevel(difficultyLevel);
        generateMap();
        displayMap();
    }

    public void setDifficultyLevel(int difficultyLevel) {
        GameEngine.difficultyLevel = difficultyLevel;
    }

    public void generateMap() {
        initializeMap();
        placeExit();
        placeEntrance();
        placeEggs();
        placeLocks();
        placeKeys();
    }

    public void initializeMap() {
        map = new char[10][10];
        entranceCell = new int[]{9, 0};
        exitCell = new int[]{0, 9};
        eggCounter = 0;
        lockCounter = 0;
        keyCounter = 0;
        stepCounter = 0;
        gameWon = false;
    }

    private void placeExit() {
        map[exitCell[0]][exitCell[1]] = 'E';
    }

    public void placeEntrance() {
        map[entranceCell[0]][entranceCell[1]] = 'P';
    }

    public void placeEggs() {
        for (int i = 0; i < 5; i++) {
            int[] randomCell = getRandomEmptyCell();
            map[randomCell[0]][randomCell[1]] = 'O';
            eggCounter++;
        }
    }

    private void placeLocks() {
        for (int i = 0; i < difficultyLevel; i++) {
            int[] randomCell = getRandomEmptyCell();
            map[randomCell[0]][randomCell[1]] = 'L';
            lockCounter++;
        }
    }

    private void placeKeys() {
        for (int i = 0; i < 5; i++) {
            int[] randomCell = getRandomEmptyCell();
            map[randomCell[0]][randomCell[1]] = 'K';
            keyCounter++;
        }
    }

    private int[] getRandomEmptyCell() {
        Random random = new Random();
        int row;
        int col;
        do {
            row = random.nextInt(10);
            col = random.nextInt(10);
        } while (map[row][col] != '\u0000');
        return new int[]{row, col};
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (!gameWon) {
            System.out.print("Enter your move (u/d/l/r): ");
            input = scanner.nextLine();

            switch (input) {
                case "u":
                    movePlayer(-1, 0);
                    break;
                case "d":
                    movePlayer(1, 0);
                    break;
                case "l":
                    movePlayer(0, -1);
                    break;
                case "r":
                    movePlayer(0, 1);
                    break;
                case "HELP":
                    displayHelp();
                    break;
                case "STATUS":
                    displayStatus();
                    break;
                default:
                    System.out.println("Invalid move. Please try again.");
            }
            checkGameStatus();

        }
    }

    private void checkGameStatus() {
        if (entranceCell[0] == exitCell[0] && entranceCell[1] == exitCell[1]) {
            if (ownedEgg == eggCounter) {
                gameWon = true;
                System.out.println("Congratulations! You finished within the time limit.");
                System.out.println("Your score: " + stepCounter);
                return;
            }else { //ownedEgg < 5
                System.out.printf("%d of eggs still to be collected\n", eggCounter - ownedEgg);
            }

        }

        if (stepCounter == timeLimit){
            System.out.println("Time's up! You ran out of steps.");
            System.out.println("You can do better next time. Keep practicing!");
        }

    }


    public void movePlayer(int rowOffset, int colOffset) {
        int newRow = entranceCell[0] + rowOffset;
        int newCol = entranceCell[1] + colOffset;

        if (isValidMove(newRow, newCol)) {
            map[entranceCell[0]][entranceCell[1]] = '\u0000';
            entranceCell[0] = newRow;
            entranceCell[1] = newCol;
            map[entranceCell[0]][entranceCell[1]] = 'P';
            stepCounter++;
            displayMap();
            System.out.printf("Egg Counter: %d\n", ownedEgg);
            System.out.printf("Key Counter: %d\n", ownedKey);
            System.out.printf("Time left: %d\n", timeLimit - stepCounter);

        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }

    private boolean isValidMove(int row, int col) {
        if (row >= 0 && row < 10 && col >= 0 && col < 10) {
            if (map[row][col] == 'L' && ownedKey != 0) {
                ownedKey--;
                return true;
            }
            if (map[row][col] == 'O') {
                collectEgg(row, col);
                return true;
            }
            if (map[row][col] == 'K') {
                collectKey(row, col);
                return true;
            }
            if (map[row][col] == 'E' && ownedEgg == 5){
                gameWon = true;
                return true;
            }
            if (map[row][col] == 'E' && ownedEgg < 5){
                System.out.printf("Number of eggs still to be collected: %d\n", eggCounter - ownedEgg);
                return false;
            }

            return map[row][col] != 'E' && map[row][col] != 'L';
        }
        return false;
    }

    private void collectEgg(int row, int col) {
        ownedEgg++;
        map[row][col] = '\u0000';
        if (eggCounter == 0 && entranceCell[0] == exitCell[0] && entranceCell[1] == exitCell[1]) {
            gameWon = true;
        }
    }

    private void collectKey(int row, int col) {
        ownedKey++;
        map[row][col] = '\u0000';
    }



    private void displayMap() {
        System.out.println("Current Map:");
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (row == entranceCell[0] && col == entranceCell[1]) {
                    System.out.print("P ");
                } else {
                    System.out.print(map[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    public void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("- u: Move player up");
        System.out.println("- d: Move player down");
        System.out.println("- l: Move player left");
        System.out.println("- r: Move player right");
        System.out.println("- HELP: Display available commands");
        System.out.println("- STATUS: Display game status");
    }

    public void displayStatus() {
        System.out.println("Egg Counter: " + eggCounter);
        System.out.println("Key Counter: " + keyCounter);
        System.out.println("Time left: " + (timeLimit - stepCounter));
    }


    public static void main(String[] args) {
        System.out.print("Welcome to Egg Hunt!!\nType 'HELP' during the game for more information.\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter difficulty level (1-10): ");
        int difficultyLevel;

        try {
            difficultyLevel = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Setting difficulty level to default value 5.");
            difficultyLevel = 5;
        }

        GameEngine gameEngine = new GameEngine();
        gameEngine.initializeGame(difficultyLevel);
        gameEngine.playGame();
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
}