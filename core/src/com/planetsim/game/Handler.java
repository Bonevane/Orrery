package com.planetsim.game;

import com.planetsim.universe.*;
import com.planetsim.camera.Camera;
import com.planetsim.input.Controller;
import com.planetsim.input.KeyManager;

public class Handler
{
    private PlanetSim application;
    private Universe universe;
    private Controller controller;

    public Handler(PlanetSim app)
    {
        this.application = app;
    }

    public Camera getCamera()
    {
        return application.getCamera();
    }

    public KeyManager getKeyManager()
    {
        return application.getKeyManager();
    }

    public int getWidth()
    {
        return application.getWidth();
    }

    public int getHeight()
    {
        return application.getHeight();
    }

    public PlanetSim getApplication()
    {
        return application;
    }

    public void setGame(PlanetSim application)
    {
        this.application = application;
    }

    public Universe getUniverse()
    {
        return universe;
    }

    public void setUniverse(Universe uni)
    {
        this.universe = uni;
    }

    public Controller getController()
    {
        return this.controller;
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    //some helper methods.
    public int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public long randomWithRange(long min, long max)
    {
        long range = (long)(max - min) + 1;
        return (long)(Math.random() * range) + min;
    }

    public int distform(int x, int y, int x1, int y1)
    {
        return ((int) Math.sqrt((Math.pow((x1-x),2))+(Math.pow((y1-y),2))));
    }

    public float distform(float x, float y, float x1, float y1)
    {
        return ((float) Math.sqrt((Math.pow((x1 - x), 2)) + (Math.pow((y1 - y), 2))));
    }
}