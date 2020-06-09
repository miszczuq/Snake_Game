package sample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main extends Application {

    private Snake snake = new Snake();
    private int speed = 7;

    private int width = 20;
    private int height = 20;

    private int foodX = 0;
    private int foodY = 0;

    private int squareSize = 25;
    private Random rand = new Random();

    private Direction direction = Direction.left;
    private boolean gameOver = false;

    public void start(Stage primaryStage) {
        try {
            newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(width * squareSize, height * squareSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * squareSize, height * squareSize);

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W  && direction != Direction.down) {
                    direction = Direction.up;
                }
                if (key.getCode() == KeyCode.A && direction != Direction.right) {
                    direction = Direction.left;
                }
                if (key.getCode() == KeyCode.S && direction != Direction.up) {
                    direction = Direction.down;
                }
                if (key.getCode() == KeyCode.D && direction != Direction.left) {
                    direction = Direction.right;
                }

            });

            // CHEAT FOR TESTING
            primaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    snake.grow();
                    speed++;
                }
            });

            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Rock Worm");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tick(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", width* squareSize /2-150, height* squareSize /2);
            return;
        }

        moveSnake();
        eatingAction();

        if(!gameOver) {
            gameOver = isCrashedOnSelf();
        }

        drawBackground(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore(gc);
    }

    void moveSnake(){
        //moving body parts on next position
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        //moving head in called direction
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y >= height) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x >= width) {
                    gameOver = true;
                }
                break;
        }
    }

    void eatingAction(){
        if (foodX == snake.get(0).x && foodY == snake.get(0).y){
            snake.grow();
            newFood();
        }
    }

    boolean isCrashedOnSelf(){
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                return true;
            }
        }
        return false;
    }

    void drawBackground(GraphicsContext gc){
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Michał Miszczuk\\IdeaProjects\\Gui_Project_III\\src\\sample\\img\\grass.jpg");
            Image grass = new Image(fis);
            gc.drawImage(grass, 0, 0,width* squareSize, height* squareSize);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawFood(GraphicsContext gc){
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\Michał Miszczuk\\IdeaProjects\\Gui_Project_III\\src\\sample\\img\\meat.png");
            Image food = new Image(fis);
            gc.drawImage(food, foodX* squareSize, foodY* squareSize, squareSize, squareSize);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawSnake(GraphicsContext gc){
        for (BodyPart c : snake.getBody()) {
            gc.setFill(Color.rgb(120,100,80));
            gc.fillRect(c.x * squareSize, c.y * squareSize, squareSize - 1, squareSize - 1);
            gc.setFill(Color.rgb(90,75,50));
            gc.fillRect(c.x * squareSize, c.y * squareSize, squareSize - 2, squareSize - 2);


            // Klatkuje :(
            /*try {
                FileInputStream fis = new FileInputStream("C:\\Users\\Michał Miszczuk\\IdeaProjects\\Gui_Project_III\\src\\sample\\img\\skin.png");
                Image skin = new Image(fis);
                gc.drawImage(skin, c.x * squareSize, c.y * squareSize, squareSize, squareSize);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    void drawScore(GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (speed - 6), 10, 30);
    }

    //generate food
    public void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (BodyPart c : snake.getBody()) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            speed++;
            break;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

