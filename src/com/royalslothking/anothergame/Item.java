package com.royalslothking.anothergame;

public class Item {

    String name;
    Thing.ThingType thingType;
    int id;

    Item(String name){
        this.name = name;
    }

    Item(String name, Thing.ThingType thingType){
        this.name = name;
        this.thingType = thingType;
    }

    Item(String name, Thing.ThingType thingType, int id){
        this.name = name;
        this.thingType = thingType;
        this.id = id;
    }

}
