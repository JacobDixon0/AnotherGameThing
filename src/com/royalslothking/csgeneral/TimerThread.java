package com.royalslothking.csgeneral;

public class TimerThread extends Thread{

    public void run(){

        System.out.println("thread initiated");

        try {
            this.sleep(1000);
        }catch (InterruptedException e){
            System.out.println("A fatal error occurred, closing application.");
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            this.join();
        }catch (InterruptedException e){
            System.out.println("A fatal error occurred, closing application.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
