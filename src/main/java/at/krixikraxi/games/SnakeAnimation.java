package at.krixikraxi.games;

import at.krixikraxi.games.util.SnakeConstants;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SnakeAnimation extends AnimationTimer {

    private GraphicsContext graphicsContext;
    private Random rand;
    private long sleepNs = 0;
    private long prevTime = 0;

    private int x = 0;
    private int y = 0;
    private int xSpeed = 1;
    private int ySpeed = 0;
    private int snakeLength = 0;

    private List<SneakPoint> pointList;
    private SneakPoint food = null;

    public SnakeAnimation(GraphicsContext graphicsContext, long sleepMs) {
        this.graphicsContext = graphicsContext;
        this.sleepNs = sleepMs * 1000000;
        rand = new Random();
        food = produceFood();
        pointList = new LinkedList<>();
    }

    @Override
    public void handle(long now) {
        if ((now - prevTime) < sleepNs) {
            return;
        }
        updateTheField();
        prevTime = now;
    }
    
    private void updateTheField() {
        graphicsContext.clearRect(0, 0, SnakeConstants.CANVAS_WITH, SnakeConstants.CANVAS_HEIGTH);

        if(eat(food)) {
            food = produceFood();
        }
        showFoodOnCanvas(food);
        moveSnake();

        pointList.add(new SneakPoint(x,y));
        if(pointList.size() > snakeLength) {
            pointList.remove(0);
        }

        // paint sneak
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(x, y, SnakeConstants.CANVAS_SCALE, SnakeConstants.CANVAS_SCALE);
        for (SneakPoint s : pointList) {
            graphicsContext.fillRect(s.getX(), s.getY(), SnakeConstants.CANVAS_SCALE, SnakeConstants.CANVAS_SCALE);
        }
    }

    private void moveSnake() {
        y = y + (ySpeed * SnakeConstants.CANVAS_SCALE);
        x = x + (xSpeed * SnakeConstants.CANVAS_SCALE);

        if (x < 0) {
            x = 0;
        } else if (x > (SnakeConstants.CANVAS_WITH - SnakeConstants.CANVAS_SCALE)) {
            x = (SnakeConstants.CANVAS_WITH - SnakeConstants.CANVAS_SCALE);
        } else if (y < 0) {
            y = 0;
        } else if (y > (SnakeConstants.CANVAS_HEIGTH - SnakeConstants.CANVAS_SCALE)) {
            y = (SnakeConstants.CANVAS_HEIGTH - SnakeConstants.CANVAS_SCALE);
        }
    }

    private boolean eat(SneakPoint f) {
        if(f.getX() == x && f.getY() == y) {
            snakeLength++;
            return true;
        }
        return false;
    }

    private SneakPoint produceFood() {
        int row = (int) Math.floor((SnakeConstants.CANVAS_HEIGTH / SnakeConstants.CANVAS_SCALE));
        int col = (int) Math.floor((SnakeConstants.CANVAS_WITH / SnakeConstants.CANVAS_SCALE));
        return new SneakPoint(rand.nextInt(row)*SnakeConstants.CANVAS_SCALE, rand.nextInt(col)*SnakeConstants.CANVAS_SCALE);
    }

    private void showFoodOnCanvas(SneakPoint f) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillRect(f.getX(), f.getY(), SnakeConstants.CANVAS_SCALE, SnakeConstants.CANVAS_SCALE);
    }

    // todo: check if moving backwards
    public void changeDirection(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    private class SneakPoint {
        private int x;
        private int y;

        public SneakPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }


}
