package egghunt.gui;

import egghunt.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Controller {
    private GameEngine gameEngine;

    @FXML
    private VBox rightroot;

    @FXML
    private Button startGameButton;
    @FXML
    private Button btUp;
    @FXML
    private Button btDw;
    @FXML
    private Button btRight;
    @FXML
    private Button btLeft;
    @FXML
    private Button btHelp;
    @FXML
    private Button btStatus;
    @FXML
    private TextField difficultyLevelTextField;

    public Controller() {
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @FXML
    private void initialize() {
        startGameButton.setOnAction(event -> {
            gameEngine.initializeGame(gameEngine.getDifficultyLevel());
            gameEngine.playGame();
        });
    }

    @FXML
    private void handleUpButtonAction() {
        gameEngine.movePlayer(-1, 0);
    }

    @FXML
    private void handleDownButtonAction() {
        gameEngine.movePlayer(1, 0);
    }

    @FXML
    private void handleRightButtonAction() {
        gameEngine.movePlayer(0, 1);
    }

    @FXML
    private void handleLeftButtonAction() {
        gameEngine.movePlayer(0, -1);
    }

    @FXML
    private void handleHelpButtonAction() {
        gameEngine.displayHelp();
    }

    @FXML
    private void handleStatusButtonAction() {
        gameEngine.displayStatus();
    }


    @FXML
    private void handleStartGameButtonAction() {
        String levelText = difficultyLevelTextField.getText();
        try {
            int difficultyLevel = Integer.parseInt(levelText);
            gameEngine.initializeGame(difficultyLevel);
            gameEngine.playGame();
        } catch (NumberFormatException e) {
            System.out.println("Invalid difficulty level. Please enter a valid number.");
        }
    }

}
