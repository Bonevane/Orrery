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

    public int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}