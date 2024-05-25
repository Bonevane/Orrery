package com.planetsim.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.game.Handler;

public abstract class State
{

    private static State currentState = null;

    public static void setState(State state)
    {
        currentState = state;
    }

    public static State getState()
    {
        return currentState;
    }


    protected Handler handler;

    public State(Handler handler)
    {
        this.handler = handler;
    }

    public abstract void tick();

    public abstract void render(ShapeRenderer g);

}
