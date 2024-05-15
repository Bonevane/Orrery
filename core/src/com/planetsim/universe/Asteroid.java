package com.planetsim.universe;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.game.Handler;

public class Asteroid extends Entity
{
    public Asteroid(Handler handler, float x, float y, long mass)
    {
        super(handler, x, y, mass);
        this.density = 40;
        calculateRadius(mass);
    }

    public Asteroid(Handler handler, float x, float y, long mass, Vector v)
    {
        super(handler, x, y, mass);
        this.density = 40;
        calculateRadius(mass);
        this.velocity = v;
    }

    @Override
    public void tick()
    {
        calculateAttraction();
    }

    @Override
    public void render(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Filled);
        int zoomDiameter = (int) (radius/handler.getCamera().getZoomLevel()*2);
        g.setColor(200/255.0f,200/255.0f,200/255.0f, 1);
        if(zoomDiameter >= 2)
            g.circle((int)(drawX), (int)(drawY), zoomDiameter);
        else
            g.circle((int)(drawX), (int)(drawY), 2);
        g.end();
    }
}
