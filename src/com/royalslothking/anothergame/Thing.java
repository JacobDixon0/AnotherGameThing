package com.royalslothking.anothergame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Thing {

    private ImageView imgSprite = new ImageView();
    private Image sprite;

    private ThingType type;

    private Direction rotation;
    private boolean rotatable = false;

    private static Image defaultSprite = new Image("/com/royalslothking/anothergame/assets/textures/objects/block.png");

    private static Image beltSprite = new Image("/com/royalslothking/anothergame/assets/textures/objects/belt.png");
    private static Image beltSpriteR = new Image("/com/royalslothking/anothergame/assets/textures/objects/beltR.png");
    private static Image beltSpriteL = new Image("/com/royalslothking/anothergame/assets/textures/objects/beltL.png");
    private static Image beltSpriteU = new Image("/com/royalslothking/anothergame/assets/textures/objects/beltU.png");
    private static Image beltSpriteD = new Image("/com/royalslothking/anothergame/assets/textures/objects/beltD.png");

    private double beltSpeed = 10.0;

    enum ThingType{
        NONE, GENERIC, ITEM, BELT
    }

    Thing(double x, double y, Image sprite){
        this.type = ThingType.NONE;
        this.rotation = Direction.NONE;

        imgSprite.setImage(sprite);
        imgSprite.setX(x);
        imgSprite.setY(y);
        imgSprite.setFitHeight(sprite.getHeight());
        imgSprite.setFitWidth(sprite.getWidth());
    }

    Thing(double x, double y, ThingType type){
        this.type = type;
        this.rotation = Direction.NONE;

        imgSprite.setX(x);
        imgSprite.setY(y);
        if(type == ThingType.BELT){
            rotatable = true;
            imgSprite.setImage(beltSprite);
            imgSprite.setFitHeight(beltSprite.getHeight());
            imgSprite.setFitWidth(beltSprite.getWidth());
        }else {
            imgSprite.setImage(defaultSprite);
            imgSprite.setFitHeight(defaultSprite.getHeight());
            imgSprite.setFitWidth(defaultSprite.getWidth());
        }
    }

    Thing(double x, double y, ThingType type, Direction direction){
        this.type = type;
        this.rotation = direction;

        imgSprite.setX(x);
        imgSprite.setY(y);
        if(type == ThingType.BELT){
            rotatable = true;
            if(direction == Direction.NONE) {
                imgSprite.setImage(beltSprite);
            }else if(direction == Direction.RIGHT){
                imgSprite.setImage(beltSpriteR);
            }else if(direction == Direction.LEFT){
                imgSprite.setImage(beltSpriteL);
            }else if(direction == Direction.UP){
                imgSprite.setImage(beltSpriteU);
            }else if(direction == Direction.DOWN){
                imgSprite.setImage(beltSpriteD);
            }
            imgSprite.setFitHeight(beltSprite.getHeight());
            imgSprite.setFitWidth(beltSprite.getWidth());
        }else {
            imgSprite.setImage(defaultSprite);
            imgSprite.setFitHeight(defaultSprite.getHeight());
            imgSprite.setFitWidth(defaultSprite.getWidth());
        }
    }

    public void rightClickEvent(){
        if(type == ThingType.BELT) {
            Main.pushMsg("the type \"BELT\" does not have a right click event, so... yeah...");
        }else{
            Main.pushMsg("yo wassup");
            GUI.menuGeneric();
        }
    }

    public void collisionEvent(Player player){

        if(type == ThingType.BELT){
            if(rotation == Direction.RIGHT){
                player.vX += beltSpeed;
            }else if(rotation == Direction.LEFT){
                player.vX += -beltSpeed;
            }else if(rotation == Direction.UP){
                player.vY += -beltSpeed;
            }else if(rotation == Direction.DOWN){
                player.vY += beltSpeed;
            }
        }

    }

    public double getX(){
        return(imgSprite.getX());
    }

    public double getY(){
        return(imgSprite.getY());
    }

    public double getRealX(){
        return(imgSprite.getX() + Main.origin[0]);
    }

    public double getRealY(){
        return(imgSprite.getY() + Main.origin[1]);
    }

    public double getWidth(){
        return imgSprite.getFitWidth();
    }

    public double getHeight(){
        return imgSprite.getFitHeight();
    }

    public ThingType getType(){
        return this.type;
    }

    public Direction getRotation(){
        return rotation;
    }

    public ImageView getImgSprite(){
        return imgSprite;
    }

    public void setX(double x){
        imgSprite.setX(x);
    }

    public void setY(double y){
        imgSprite.setY(y);
    }

    public void setType(ThingType type){
        this.type = type;
    }

    public void setRotation(Direction rotation){
        this.rotation = rotation;
    }

    public void setImgSprite(Image img){
        imgSprite.setImage(img);
    }

    public Image getSprite(){
        return imgSprite.getImage();
    }

    // this is not an efficient way to do this but that how it B.
    public static Image getSpriteByType(ThingType type){
        if(type == ThingType.NONE){
            return defaultSprite;
        }else if(type == ThingType.BELT){
            return beltSprite;
        }
        return null;
    }

}
