package com.royalslothking.anothergame;

public class Collision {

    public static boolean collidingR = false;
    public static boolean collidingL = false;
    public static boolean collidingT = false;
    public static boolean collidingB = false;


    public static Thing isCollidingRight(Player player){

        for(Object obj : Main.mapObjects){

            if(obj instanceof Thing) {
                Thing t = ((Thing) obj);

                if(t.getType() != Thing.ThingType.BELT) {
                    if (player.getX() + player.getFitWidth() >= t.getX() &&
                            player.getX() < t.getX() + t.getWidth() &&
                            player.getY() < t.getY() + t.getHeight() &&
                            player.getY() + player.getFitHeight() > t.getY()) {
                        collidingR = true;
                        return t;
                    }
                }

                /*if(player.getX() + player.getFitWidth() >= t.getX() &&
                        player.getX() < t.getX() + t.getWidth() &&
                        player.getY() + player.getFitHeight() > t.getY() &&
                        player.getY() < t.getY() + t.getHeight()){
                    return t;
                }*/

            }

        }
        collidingR = false;
        return null;
    }

    public static Thing isCollidingLeft(Player player){

        for(Object obj : Main.mapObjects){

            if(obj instanceof Thing) {
                Thing t = ((Thing) obj);

                if(t.getType() != Thing.ThingType.BELT) {
                    if (player.getX() <= t.getX() + t.getWidth() &&
                            player.getX() + player.getFitWidth() > t.getX() &&
                            player.getY() < t.getY() + t.getHeight() &&
                            player.getY() + player.getFitHeight() > t.getY()) {
                        collidingL = true;
                        return t;
                    }
                }

                /*if(player.getX() <= t.getX() + t.getWidth() &&
                    player.getX() + player.getFitWidth() > t.getX() &&
                    player.getY() + player.getFitHeight() > t.getY() &&
                    player.getY() < t.getY() + t.getHeight()){
                    return t;
                }*/

            }

        }
        collidingL = false;
        return null;
    }

    public static Thing isCollidingTop(Player player){

        for(Object obj : Main.mapObjects){

            if(obj instanceof Thing) {
                Thing t = ((Thing) obj);

                if(t.getType() != Thing.ThingType.BELT) {
                    if (player.getY() <= t.getY() + t.getHeight() &&
                            player.getY() + player.getFitHeight() > t.getY() &&
                            player.getX() + player.getFitWidth() > t.getX() &&
                            player.getX() < t.getX() + t.getWidth()) {
                        collidingT = true;
                        return t;
                    }
                }

            }

        }
        collidingT = false;
        return null;
    }

    public static Thing isCollidingBottom(Player player){

        for(Object obj : Main.mapObjects){

            if(obj instanceof Thing) {
                Thing t = ((Thing) obj);

                if(t.getType() != Thing.ThingType.BELT) {
                    if (player.getY() + player.getFitHeight() >= t.getY() &&
                            player.getY() < t.getY() + t.getHeight() &&
                            player.getX() + player.getFitWidth() > t.getX() &&
                            player.getX() < t.getX() + t.getWidth()) {
                        collidingB = true;
                        return (t);
                    }
                }

            }

        }
        collidingB = false;
        return null;
    }

    public static Thing isCollidingBelt(Player player){
        for(Object obj : Main.mapObjects){
            if(obj instanceof Thing) {
                Thing t = ((Thing) obj);
                if(t.getType() == Thing.ThingType.BELT) {
                    if (player.getX() + player.getFitWidth() >= t.getX() &&
                            player.getX() < t.getX() + t.getWidth() &&
                            player.getY() + (player.getFitHeight()/2) < t.getY() + t.getHeight() &&
                            player.getY() + player.getFitHeight() > t.getY()) {
                        return t;
                    }
                }
            }
        }
        return null;
    }

    public static boolean colliding(){
        return(isCollidingRight(Main.player) != null || isCollidingLeft(Main.player) != null  || isCollidingTop(Main.player) != null  || isCollidingBottom(Main.player) != null );
    }
}
