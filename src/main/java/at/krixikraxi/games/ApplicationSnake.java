package at.krixikraxi.games;

import at.krixikraxi.games.util.SnakeConstants;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 */
public class ApplicationSnake extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake!");
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);

        Canvas canvas = new Canvas(SnakeConstants.CANVAS_WITH, SnakeConstants.CANVAS_HEIGTH);
        canvas.setFocusTraversable(true);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        VBox root = new VBox();
        HBox stats = new HBox();
        //stats.setStyle("-fx-border-color: black");
        root.getChildren().add(canvas);
        root.getChildren().add(stats);
        primaryStage.setScene(new Scene(root));

        Label points = new Label();
        stats.getChildren().add(points);

        SnakeAnimation snakeAnimation = new SnakeAnimation(graphicsContext, SnakeConstants.SPEED, points);
        snakeAnimation.start();

        canvas.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
            if(key.getCode() == KeyCode.UP) {
                snakeAnimation.changeDirection(0, -1);
            } else if (key.getCode() == KeyCode.DOWN) {
                snakeAnimation.changeDirection(0, 1);
            } else if (key.getCode() == KeyCode.LEFT) {
                snakeAnimation.changeDirection(-1, 0);
            } else if (key.getCode() == KeyCode.RIGHT) {
                snakeAnimation.changeDirection(1, 0);
            }
        });

        primaryStage.show();
    }
}
