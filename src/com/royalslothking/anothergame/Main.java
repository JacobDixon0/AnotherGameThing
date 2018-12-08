package com.royalslothking.anothergame;

/*
 *  Author: Jacob Dixon @jacobrdixon.com
 *  Date: 6/12/2018
 *
 *  If you are not Jacob Dixon, please leave, thx.
 *
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static Stage stage;

    public static String gameTitle = "game";

    public static double WINDOW_SIZE_X = 1280.0;
    public static double WINDOW_SIZE_Y = 720.0;

    private static final double FRAME_RATE_LIMIT = 16.67;
    private static long lastUpdateTime = System.currentTimeMillis();

    public static boolean allowCommands = true;

    private static final long[] frameTimes = new long[100];
    private static int frameTimeIndex = 0;
    private static boolean arrayFilled = false;
    private static Label fpsDisplay = new Label();

    public static Pane root = new Pane();

    public static AnimationTimer timer;

    public static HashMap<KeyCode, Boolean> keys = new HashMap<>();

    public static double[] origin = {0, 0};

    public static Image playerSprite = new Image("/com/royalslothking/anothergame/assets/sprites/player/player.png");
    public static Player player = new Player(WINDOW_SIZE_X / 2 - (playerSprite.getWidth() / 2), Math.round(WINDOW_SIZE_Y / 2 - (playerSprite.getHeight() / 2)), playerSprite);

    public static ArrayList<Object> mapObjects = new ArrayList<>();

    private static Image zeroMarkerIMG = new Image("/com/royalslothking/anothergame/assets/textures/objects/what.png");
    private static Thing zeroMarker = new Thing(0, 0, zeroMarkerIMG);

    public static boolean inMenu = false;

    private static Text message;
    private static int msgTimer = 0;
    private static boolean msgTimerRunning = false;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        stage.setScene(GUI.mainMenu());
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setFullScreen(true);
        stage.setTitle(gameTitle);
        stage.getIcons().add(new Image("/com/royalslothking/anothergame/assets/application/icon.png"));
        stage.setOnCloseRequest(e ->  exit());
        stage.show();

    }

    public static Scene getScene(){

        Scene scene = new Scene(root, WINDOW_SIZE_X, WINDOW_SIZE_Y);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        for( Object o : mapObjects){
            if(o instanceof Rectangle) {
                Rectangle r = ((Rectangle) o);
                root.getChildren().add(r);
            }else if(o instanceof ImageView){
                ImageView iv = ((ImageView) o);
                root.getChildren().add(iv);
            }else if(o instanceof Thing){
                Thing t = ((Thing) o);
                root.getChildren().add(t.getImgSprite());
            }
        }

        root.getChildren().add(zeroMarker.getImgSprite());
        GUI.addStaticGUI();

        fpsDisplay.setTextFill(Color.GREEN);
        root.getChildren().add(fpsDisplay);
        root.getChildren().add(player);

        scene.getStylesheets().add("/com/royalslothking/anothergame/assets/gui/style.css");
        scene.setCursor(new ImageCursor(new Image("/com/royalslothking/anothergame/assets/gui/cursor0.png")));

        scene.setOnKeyPressed(e -> {
            keys.put(e.getCode(), true);
            player.onKeyPressed(e.getCode());
        });
        scene.setOnKeyReleased(e -> keys.put(e.getCode(), false));
        scene.setOnMouseClicked(e -> player.onClick(e.getX(), e.getY(), e.getButton()));
        scene.setOnMouseMoved(e -> player.onHover(e.getX(), e.getY()));

        return scene;

    }

    public static boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public static void update(){
        long now = System.currentTimeMillis();

        if(lastUpdateTime + FRAME_RATE_LIMIT <= now) {

            long oldFrameTime = frameTimes[frameTimeIndex];
            frameTimes[frameTimeIndex] = now;
            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
            if (frameTimeIndex == 0) {
                arrayFilled = true;
            }

            if (arrayFilled) {
                long elapsedMills = now - oldFrameTime;
                long elapsedMillsPerFrame = elapsedMills / frameTimes.length;
                double frameRate = 1000.0 / elapsedMillsPerFrame;
                fpsDisplay.setText(String.format("FPS: %.2f", frameRate));
            }

            player.onUpdate();

            for (Object obj : mapObjects) {

                if (obj instanceof Rectangle) {
                    Rectangle r = ((Rectangle) obj);

                    r.setX(r.getX() + player.vX * -1);
                    r.setY(r.getY() + player.vY * -1);
                } else if (obj instanceof ImageView) {
                    ImageView i = ((ImageView) obj);

                    i.setX(i.getX() + player.vX * -1);
                    i.setY(i.getY() + player.vY * -1);
                } else if (obj instanceof Thing) {
                    Thing t = ((Thing) obj);

                    t.setX(t.getX() + player.vX * -1);
                    t.setY(t.getY() + player.vY * -1);
                }
            }

            origin[0] += player.vX;
            origin[1] += player.vY;

            zeroMarker.setX(origin[0] * -1);
            zeroMarker.setY(origin[1] * -1);

            // multithreading is scary so this is how it b
            if (msgTimerRunning) {
                msgTimer += 1;
                if (msgTimer >= 100) {
                    root.getChildren().remove(message);
                    msgTimerRunning = false;
                }
            }

            lastUpdateTime = System.currentTimeMillis();

        }

    }

    public static double[] mapBlockToGrid(double x, double y){

        double[] result = {x, y};

        result[0] += (origin[0] % 100);
        result[1] += (origin[1] % 100);

        result[0] -= result[0] % 100;
        result[1] -= result[1] % 100;

        result[0] -= origin[0] % 100;
        result[1] -= origin[1] % 100;

        return result;
    }

    public static double[] mapToGrid(double x, double y){

        double[] result = {x, y};

        result[0] += origin[0];
        result[1] += origin[1];

        result[0] -= result[0] % 100;
        result[1] -= result[1] % 100;

        result[0] /= 100;
        result[1] /= 100;

        return result;
    }

    public static double[] mapToOrigin(double x, double y){

        double result[] = {x, y};

        return result;
    }

    public static Thing findBlockByLocation(double x, double y){

        // I don't want to fix the underlying issues here so, bodge time.

        if(x + origin[0] < 0){
            x -= 100.0;
        }

        if(y + origin[1] < 0){
            y -= 100.0;
        }

        x = mapToGrid(x, y)[0] * 100;
        y = mapToGrid(x, y)[1] * 100;

        for(Object obj : mapObjects){
            Thing t = ((Thing) obj);
            if(t.getRealX() == x && t.getRealY() == y){
                return t;
            }

        }
        return null;
    }

    public static void addMapObject(Thing t){
        root.getChildren().add(t.getImgSprite());
        mapObjects.add(t);
        player.toFront();
        GUI.sgToFront();
        pushMsg("Added thing with type " +  t.getType().name() + " and rotation " + t.getRotation() + " at x=" + ((int) mapToGrid(t.getX(), t.getY())[0]) + " y=" + ((int) mapToGrid(t.getX(), t.getY())[1]));

    }

    @SuppressWarnings("Duplicates")
    public static void pushMsg(String msg){

        if(root.getChildren().contains(message)) {
            root.getChildren().remove(message);
        }
        message = new Text(msg);
        message.setX(10);
        message.setY(WINDOW_SIZE_Y - 10);
        root.getChildren().add(message);
        msgTimer = 0;
        msgTimerRunning = true;
    }

    @SuppressWarnings("Duplicates")
    public static void pushMsg(String msg, int priority){

        if(root.getChildren().contains(message)) {
            root.getChildren().remove(message);
        }
        message = new Text(msg);
        message.setX(10);
        message.setY(WINDOW_SIZE_Y - 10);
        if(priority == 0){
            message.setFill(Color.RED);
        }else if(priority == 1){
            message.setFill(Color.YELLOW);
        }
        root.getChildren().add(message);
        msgTimer = 0;
        msgTimerRunning = true;
    }

    public static void removeTXT(){
        root.getChildren().remove(message);
    }

    public static void exit(){
        System.out.println("Closing application...");
        stage.close();
    }

}
