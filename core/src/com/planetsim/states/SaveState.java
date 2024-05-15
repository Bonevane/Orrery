package com.planetsim.states;

import com.planetsim.game.Handler;
import com.planetsim.universe.Planet;
import com.planetsim.universe.Star;
import com.planetsim.universe.BlackHole;
import com.planetsim.universe.Vector;

import java.io.*;
import java.util.Scanner;

public class SaveState{
    private Handler handler;

    public SaveState(Handler handler){
        this.handler = handler;
    }

    public void saveState(){
        try {
            File save = new File("save.txt");
            save.createNewFile();

            FileWriter writer = new FileWriter("save.txt");

            writer.write(handler.getApplication().getCamera().getCamera().zoom + "\n");
            writer.write(handler.getApplication().getCamera().getCamera().position.x + "\n");
            writer.write(handler.getApplication().getCamera().getCamera().position.y + "\n");
            writer.write(handler.getApplication().getCamera().getZoomLevel() + "\n");

            for (int i = 0; i < handler.getUniverse().getEntities().size(); i++){
                writer.write(handler.getUniverse().getEntities().get(i).getClass().getSimpleName() + "\n");
                writer.write(handler.getUniverse().getEntities().get(i).getX() + "\n");
                writer.write(handler.getUniverse().getEntities().get(i).getY() + "\n");
                writer.write(handler.getUniverse().getEntities().get(i).getMass() + "\n");
                writer.write(handler.getUniverse().getEntities().get(i).getVelocity().getX() + "\n");
                writer.write(handler.getUniverse().getEntities().get(i).getVelocity().getY() + "\n");
                writer.write("----\n");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("Error when Creating / Loading file.");
            return;
        }
    }

    public void loadState(){
        String name;
        double vX, vY;
        float x, y;
        long mass;

        handler.getUniverse().reset();

        try{
            File save = new File("save.txt");
            Scanner reader = new Scanner(save);

            handler.getApplication().getCamera().getCamera().zoom = Float.parseFloat(reader.nextLine());
            handler.getApplication().getCamera().getCamera().position.x = Float.parseFloat(reader.nextLine());
            handler.getApplication().getCamera().getCamera().position.y = Float.parseFloat(reader.nextLine());
            handler.getApplication().getCamera().setZoomLevel(Integer.parseInt(reader.nextLine()));

            while (reader.hasNextLine()) {
                name = reader.nextLine();
                x = Float.parseFloat(reader.nextLine());
                y = Float.parseFloat(reader.nextLine());
                mass = Long.parseLong(reader.nextLine());
                vX = Double.parseDouble(reader.nextLine());
                vY = Double.parseDouble(reader.nextLine());
                reader.nextLine();

                if(name.equalsIgnoreCase("planet")){
                    handler.getUniverse().addEntity(new Planet(handler, x, y, mass, new Vector(vX, vY)));
                }
                else if(name.equalsIgnoreCase("star")){
                    handler.getUniverse().addEntity(new Star(handler, x, y, mass, new Vector(vX, vY)));
                }
                else if(name.equalsIgnoreCase("blackhole")){
                    handler.getUniverse().addEntity(new BlackHole(handler, x, y, mass, new Vector(vX, vY)));
                }

            }

            reader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Error when Creating / Loading file.");
            return;
        }
    }
}
