package com.planetsim.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.universe.Universe;
import com.planetsim.input.Controller;
import com.planetsim.game.Handler;

public class UniverseState extends State
{
    private Universe universe;
    private Controller controller;

    public UniverseState(Handler handler)
    {
        super(handler);
        this.universe = new Universe(handler);
        handler.setUniverse(universe);
        this.controller = handler.getController();
    }

    @Override
    public void tick()
    {
        universe.tick();
    }

    public void render(ShapeRenderer g)
    {
        universe.render(g);
        controller.render(g);
    }

}
