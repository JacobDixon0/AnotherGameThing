package com.royalslothking.csgeneral;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GUI {

    private static ImageView guiOverlay = new ImageView(new Image("/com/royalslothking/csgeneral/assets/gui/gui0.png"));

    private static Rectangle menuGenericBG = new Rectangle(0, 0, Main.WINDOW_SIZE_X, Main.WINDOW_SIZE_Y);
    private static ImageView menuGenericAE = new ImageView(new Image("/com/royalslothking/csgeneral/assets/gui/genericMenu.png"));
    private static Rectangle menuGenericS1 = new Rectangle(591, 312, 97, 97);
    private static Rectangle menuGenericS2 = new Rectangle(826, 312, 97, 97);

    private static ImageView inventoryMenuAE = new ImageView(new Image("/com/royalslothking/csgeneral/assets/gui/inventoryMenu.png"));

    private static ImageView hotbarSlot1;
    private static ImageView hotbarSlot2;
    private static ImageView hotbarSlot3;
    private static ImageView hotbarSlot4;
    private static ImageView hotbarSlot5;

    private static ImageView[] hotbarSlots = {hotbarSlot1, hotbarSlot2, hotbarSlot3, hotbarSlot4, hotbarSlot5};

    private static ArrayList<Node> activeElements = new ArrayList<>();
    private static ArrayList<Node> activeStaticElements = new ArrayList<>();

    public static void addStaticGUI(){
        Main.root.getChildren().add(guiOverlay);
        activeStaticElements.add(guiOverlay);

        double hotbarX = 429.0;
        double hotbarY = 635.0;
        double hotbarItemSize = 80.0;
        double hotbarItemBuffer = 6.0;

        for(int i = 0; i < hotbarSlots.length; i++){
            if(Main.player.inventory[i + 1] != null){
                hotbarSlots[i] = new ImageView(Thing.getSpriteByType(Main.player.inventory[i + 1].thingType));
                hotbarSlots[i].setX(hotbarX + ((hotbarItemSize + hotbarItemBuffer) * i));
                hotbarSlots[i].setY(hotbarY);
                hotbarSlots[i].setFitWidth(hotbarItemSize);
                hotbarSlots[i].setFitHeight(hotbarItemSize);
                Main.root.getChildren().add(hotbarSlots[i]);
                activeStaticElements.add(hotbarSlots[i]);
            }
        }
    }

    public static void sgToFront(){
        for(Node ase : activeStaticElements){
            ase.toFront();
        }
    }

    public static void menuGeneric(){
        if(!Main.inMenu) {
            menuGenericBG.setFill(Color.color(0, 0, 0, 0.5));

            menuGenericAE.setX(100);
            menuGenericAE.setY(100);

            menuGenericS1.setFill(Color.color(1, 1, 1, 0));
            menuGenericS1.setOnMouseEntered(e -> menuGenericS1.setFill(Color.color(1, 1, 1, 0.1)));
            menuGenericS1.setOnMouseExited(e -> menuGenericS1.setFill(Color.color(1, 1, 1, 0)));

            menuGenericS2.setFill(Color.color(1, 1, 1, 0));
            menuGenericS2.setOnMouseEntered(e -> menuGenericS2.setFill(Color.color(1, 1, 1, 0.1)));
            menuGenericS2.setOnMouseExited(e -> menuGenericS2.setFill(Color.color(1, 1, 1, 0)));

            Main.root.getChildren().addAll(menuGenericBG, menuGenericAE, menuGenericS1, menuGenericS2);

            activeElements.add(menuGenericBG);
            activeElements.add(menuGenericAE);
            activeElements.add(menuGenericS1);
            activeElements.add(menuGenericS2);

            Main.inMenu = true;
        }
    }

    public static void playerInventoryMenu(){
        if(!Main.inMenu){

            menuGenericBG.setFill(Color.color(0, 0, 0, 0.5));

            inventoryMenuAE.setX(100);
            inventoryMenuAE.setY(100);

            Main.root.getChildren().addAll(menuGenericBG, inventoryMenuAE);

            activeElements.add(menuGenericBG);
            activeElements.add(inventoryMenuAE);

            Main.inMenu = true;

        }
    }

    public static Scene mainMenu(){

        Pane root = new Pane();
        Scene scene = new Scene(root, Main.WINDOW_SIZE_X, Main.WINDOW_SIZE_Y);

        ImageView bg = new ImageView(new Image("/com/royalslothking/csgeneral/assets/application/mainMenuBG.png"));

        Button btn = new Button("Start");
        btn.setLayoutX(Main.WINDOW_SIZE_X / 2);
        btn.setLayoutY(Main.WINDOW_SIZE_Y / 2);
        btn.setOnAction(e -> Main.stage.setScene(Main.getScene()));

        Button btn2 = new Button("Exit");
        btn2.setLayoutX(Main.WINDOW_SIZE_X / 2);
        btn2.setLayoutY(Main.WINDOW_SIZE_Y / 2 + 30);
        btn2.setOnAction(e -> Main.exit());

        root.getChildren().addAll(bg, btn, btn2);

        return scene;
    }

    public static void exitMenu(){
        for(Node node : activeElements){
            Main.root.getChildren().remove(node);
        }
        activeElements.clear();
        Main.inMenu = false;
    }
}
