package com.royalslothking.anothergame;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {

    public static void open(){

        if(!Main.allowCommands){
            Main.pushMsg("Commands are not enabled.", 0);
            return;
        }

        Main.stage.setTitle(Main.gameTitle + " - CONSOLE");
        Scanner s = new Scanner(System.in);
        System.out.print("> ");

        String in = s.nextLine();

        Matcher commandPlayerspeed = Pattern.compile("^/playerspeed ([0-9]+(?:.[0-9]+)?)$").matcher(in);
        Matcher commandGive = Pattern.compile("^/give ([A-Za-z-_]+)$").matcher(in);
        Matcher commandHelp = Pattern.compile("^/help").matcher(in);

        if(commandPlayerspeed.find()){
            playerSpeed(commandPlayerspeed.group(1));
        } else if(commandGive.find()){
            give(commandGive.group(1));
        } else if(commandHelp.find()){
            help();
        } else{
            noCommand(in);
        }

        Main.stage.setTitle(Main.gameTitle);

    }

    private static void give(String arg){

        boolean failed = false;

        if(arg.toLowerCase().equals("default")){
            for(int i = 1; i < Main.player.inventory.length; i++){
                if(Main.player.inventory[i] == null){
                    Main.player.inventory[i] = Player.thing;
                    Main.pushMsg("Added item with type \"NONE\" to inventory in slot " + i + ".");
                    failed = false;
                    break;
                }else{
                    failed = true;
                }
            }
        }else if(arg.toLowerCase().equals("belt")){
            for(int i = 1; i < Main.player.inventory.length; i++){
                if(Main.player.inventory[i] == null){
                    Main.player.inventory[i] = Player.belt;
                    Main.pushMsg("Added item with type \"BELT\" to inventory in slot " + i + ".");
                    failed = false;
                    break;
                }else{
                    failed = true;
                }
            }
        }else{
            System.out.println("\"" + arg + "\"" + " is not an item.");
        }

        if(failed){
            System.out.println("Did not add item, inventory full.");
            Main.pushMsg("Did not add item, inventory full.", 0);
        }

        GUI.rmStaticGUI();
        GUI.addStaticGUI();

    }

    private static void playerSpeed(String arg){
        try {
            Main.player.playerSpeed = Double.parseDouble(arg);
            Main.pushMsg("Player Speed: " + arg, 0);
        }catch(NumberFormatException e){
            System.out.println("\"" + arg + "\"" + " is not a number.");
        }
    }

    private static void help(){
        System.out.println("playerspeed [double] - Changes the players speed.");
    }

    private static void noCommand(String arg){
        System.out.println("\"" + arg + "\"" + " is not a valid command.");
    }

}
