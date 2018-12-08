package com.royalslothking.anothergame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class Player extends ImageView {

    public static int hp = 100;

    private Image sprite;

    public double playerSpeed = 20.0;

    public double vX;
    public double vY;

    public static boolean isMovingLeft;
    public static boolean isMovingRight;
    public static boolean isMovingUp;
    public static boolean isMovingDown;

    public static boolean isMoving;

    public static Item thing = new Item("Default Thing", Thing.ThingType.NONE, 0);
    public static Item belt = new Item("Belt", Thing.ThingType.BELT, 1);

    public Item[] inventory = {null, thing, belt, null, null, null};
    private static int selectedSlot = 1;

    private static Direction selectedRotation = Direction.NONE;

    private static ImageView selectedGridSquare = new ImageView(new Image("com/royalslothking/anothergame/assets/gui/selectedBlock.png"));

    Player(double x, double y, Image sprite){
        this.setX(x);
        this.setY(y);
        this.setImage(sprite);
        this.setFitHeight(sprite.getHeight());
        this.setFitWidth(sprite.getWidth());
        this.sprite = sprite;

        selectedGridSquare.setOpacity(0.5);
        Main.root.getChildren().add(selectedGridSquare);

    }

    public void onUpdate(){

        vX = 0.0;
        vY = 0.0;

        isMoving      = false;
        isMovingLeft  = false;
        isMovingRight = false;
        isMovingUp    = false;
        isMovingDown  = false;

        this.setImage(sprite);

        if(!Main.inMenu) {

            if (Collision.isCollidingLeft(this) == null && Main.isPressed(KeyCode.A)) {

                vX = playerSpeed * -1;
                isMovingLeft = true;
                isMoving = true;

                this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerL.png"));

            } else if (Collision.isCollidingRight(this) == null && Main.isPressed(KeyCode.D)) {

                vX = playerSpeed;
                isMovingRight = true;
                isMoving = true;

                this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerR.png"));
            }

            if (Collision.isCollidingTop(this) == null && Main.isPressed(KeyCode.W)) {

                vY = playerSpeed * -1;
                isMovingUp = true;
                isMoving = true;

                if (isMovingLeft) {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerUL.png"));
                } else if (isMovingRight) {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerUR.png"));
                } else {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerU.png"));
                }

            } else if (Collision.isCollidingBottom(this) == null && Main.isPressed(KeyCode.S)) {

                vY = playerSpeed;
                isMovingDown = true;
                isMoving = true;
                if (isMovingLeft) {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerDL.png"));
                } else if (isMovingRight) {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerDR.png"));
                } else {
                    this.setImage(new Image("/com/royalslothking/anothergame/assets/sprites/player/playerD.png"));
                }
            }

        }

        if(Main.isPressed(KeyCode.DIGIT1)){
            if(selectedSlot != 1) {
                selectedSlot = 1;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        } else if(Main.isPressed(KeyCode.DIGIT2)){
            if(selectedSlot != 2){
                selectedSlot = 2;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        } else if(Main.isPressed(KeyCode.DIGIT3)){
            if(selectedSlot != 3){
                selectedSlot = 3;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        } else if(Main.isPressed(KeyCode.DIGIT4)){
            if(selectedSlot != 4){
                selectedSlot = 4;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        } else if(Main.isPressed(KeyCode.DIGIT5)){
            if(selectedSlot != 5){
                selectedSlot = 5;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        } else if(Main.isPressed(KeyCode.Q)){
            if(selectedSlot != 0){
                selectedSlot = 0;
                if(inventory[selectedSlot] != null) {
                    Main.pushMsg("Selected Item: " + selectedSlot + " " + inventory[selectedSlot].thingType);
                }else{
                    Main.pushMsg("Selected Item: " + selectedSlot);
                }
            }
        }

        if(Main.isPressed(KeyCode.ESCAPE)){
            GUI.exitMenu();
        }

        if(Collision.isCollidingBelt(this) != null){
            Collision.isCollidingBelt(this).collisionEvent(this);
        }

    }

    public void onClick(double x, double y, MouseButton btn){

        if(Main.inMenu){
            return;
        }

        if(btn == MouseButton.PRIMARY){

            if(inventory[selectedSlot] != null && Main.findBlockByLocation(x, y) == null){
                Thing t = new Thing(Main.mapBlockToGrid(x, y)[0], Main.mapBlockToGrid(x, y)[1], inventory[selectedSlot].thingType, selectedRotation);
                Main.addMapObject(t);
            }else if(inventory[selectedSlot] == null){
                Main.pushMsg("No object added, slot is empty.");
            }else{
                Main.pushMsg("No object added, location occupied.");
            }

        }

        /*if(btn == MouseButton.PRIMARY && selectedSlot == 1){

        } else if(btn == MouseButton.PRIMARY && selectedSlot == 2) {

            if(Main.findBlockByLocation(x, y) == null) {
                Thing t = new Thing(Main.mapBlockToGrid(x, y)[0], Main.mapBlockToGrid(x, y)[1], inventory[0].thingType, selectedRotation);
                Main.mapObjects.add(t);
                Main.root.getChildren().add(t.getImgSprite());
                this.toFront();
                GUI.gui.toFront();
                Main.pushMsg("Added block with type \"NONE\" and rotation " + t.getRotation() + " at: x=" + ((int) Main.mapToGrid(t.getX(), t.getY())[0]) + " y=" + ((int) Main.mapToGrid(t.getX(), t.getY())[1]));
            }else{
                Main.pushMsg("Did not add block due to the space already being occupied.");
            }

        } else if(btn == MouseButton.PRIMARY && selectedSlot == 3){

            if(Main.findBlockByLocation(x, y) == null) {
                Thing t = new Thing(Main.mapBlockToGrid(x, y)[0], Main.mapBlockToGrid(x, y)[1], Thing.ThingType.BELT, selectedRotation);
                Main.mapObjects.add(t);
                Main.root.getChildren().add(t.getImgSprite());
                this.toFront();
                GUI.gui.toFront();
                Main.pushMsg("Added block with type \"BELT\" and rotation " + t.getRotation() + " at: x=" + ((int) Main.mapToGrid(t.getX(), t.getY())[0]) + " y=" + ((int) Main.mapToGrid(t.getX(), t.getY())[1]));
            }else{
                Main.pushMsg("Did not add block due to the space already being occupied.");
            }

        } else if(btn == MouseButton.PRIMARY && selectedSlot == 4){

        } else if(btn == MouseButton.PRIMARY && selectedSlot == 5){

        }*/

        if(btn == MouseButton.SECONDARY && selectedSlot != 0){
            Thing t = Main.findBlockByLocation(x, y);
            if (t != null) {
                Main.root.getChildren().remove(t.getImgSprite());
                Main.mapObjects.remove(t);
                Main.pushMsg("Removed block at: x=" + ((int) Main.mapToGrid(t.getX(), t.getY())[0]) + " y=" + ((int) Main.mapToGrid(t.getX(), t.getY())[1]));
            }

        }else if(selectedSlot == 0){
            Thing t = Main.findBlockByLocation(x, y);
            if(t != null){
                t.rightClickEvent();
            }

        }

    }

    public void onHover(double x, double y){

        if(Main.inMenu){
            return;
        }

        if(selectedSlot != 0) {

            if(selectedRotation == Direction.NONE){
                selectedGridSquare.setImage(new Image("com/royalslothking/anothergame/assets/gui/selectedBlock.png"));
            }else if(selectedRotation == Direction.RIGHT){
                selectedGridSquare.setImage(new Image("com/royalslothking/anothergame/assets/gui/selectedBlockR.png"));
            }else if(selectedRotation == Direction.LEFT){
                selectedGridSquare.setImage(new Image("com/royalslothking/anothergame/assets/gui/selectedBlockL.png"));
            }else if(selectedRotation == Direction.UP){
                selectedGridSquare.setImage(new Image("com/royalslothking/anothergame/assets/gui/selectedBlockU.png"));
            }else if(selectedRotation == Direction.DOWN){
                selectedGridSquare.setImage(new Image("com/royalslothking/anothergame/assets/gui/selectedBlockD.png"));
            }

            selectedGridSquare.setX(Main.mapBlockToGrid(x, y)[0]);
            selectedGridSquare.setY(Main.mapBlockToGrid(x, y)[1]);

            selectedGridSquare.toFront();
        }else{
            selectedGridSquare.setX(-100);
            selectedGridSquare.setY(-100);
        }

    }

    public void onKeyPressed(KeyCode key){

        if(key == KeyCode.F1){
            Console.open();
        }

        if(key == KeyCode.E){
            if(Main.inMenu) {
                GUI.exitMenu();
            }else GUI.playerInventoryMenu();
        }

        if(key == KeyCode.R){
            if(selectedRotation == Direction.NONE){
                selectedRotation = Direction.RIGHT;
            }else if(selectedRotation == Direction.RIGHT){
                selectedRotation = Direction.DOWN;
            }else if(selectedRotation == Direction.DOWN){
                selectedRotation = Direction.LEFT;
            }else if(selectedRotation == Direction.LEFT){
                selectedRotation = Direction.UP;
            }else if(selectedRotation == Direction.UP){
                selectedRotation = Direction.NONE;
            }

            Main.pushMsg("Selected Rotation: " + selectedRotation.name());

        }

    }

}
