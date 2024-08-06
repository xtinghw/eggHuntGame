import egghunt.engine.GameEngine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGameEngine {
    private GameEngine gameEngine;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        gameEngine = new GameEngine();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void testSetAndGetDifficultyLevel() {
        gameEngine.setDifficultyLevel(5);
        assertEquals(5, gameEngine.getDifficultyLevel());
    }

    @Test
    void testGenerateMap() {
        gameEngine.initializeMap();
        gameEngine.generateMap();

        // Add assertions to test the generated map
        // For example, check if the entrance and exit cells are correctly placed
        // and if the number of eggs, locks, and keys are as expected
    }

    @Test
    void testMovePlayer() {
        gameEngine.initializeMap();
        gameEngine.placeEntrance();
        gameEngine.movePlayer(-1, 0);

        // Add assertions to test the player movement
        // For example, check if the player position is updated correctly
        // and if the step counter is incremented
    }

    @Test
    void testIsValidMove() {
        gameEngine.initializeMap();
        gameEngine.placeEntrance();

        // Add assertions to test the validity of different moves
        // For example, check if moving to a valid empty cell returns true
        // and if moving to a cell with a lock without a key returns false
    }

    @Test
    void testCollectEgg() {
        gameEngine.initializeMap();
        gameEngine.placeEggs();

        // Add assertions to test collecting eggs
        // For example, check if the number of owned eggs is incremented
        // and if the collected egg is removed from the map
    }


}
