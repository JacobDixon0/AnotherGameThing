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
        Matcher commandPlayerspeed = Pattern.compile("^playerspeed ([0-9]+(.[0-9]+)?)$").matcher(in);
        Matcher commandHelp = Pattern.compile("^help").matcher(in);
        if(commandPlayerspeed.find()){
            try {
                Main.player.playerSpeed = Double.parseDouble(commandPlayerspeed.group(1));
                Main.pushMsg("Player Speed: " + commandPlayerspeed.group(1), 0);
            }catch(NumberFormatException e){
                System.out.println("\"" + commandPlayerspeed.group(1) + "\"" + " is not a number.");
            }
        }else if(commandHelp.find()){
            System.out.println("playerspeed [double] - Changes the players speed when moving.");
        } else{
            System.out.println("\"" + in + "\"" + " is not a valid command");
        }
        Main.stage.setTitle(Main.gameTitle);

    }

}
