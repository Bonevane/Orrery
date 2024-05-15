package com.planetsim.universe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.game.Handler;

public class BlackHole extends Entity
{
    public BlackHole(Handler handler, float x, float y, long mass)
    {
        super(handler, x, y, mass);
        this.density = 500;
        calculateRadius(mass);
    }

    public BlackHole(Handler handler, float x, float y, long mass, Vector v)
    {
        super(handler, x, y, mass);
        this.density = 500;
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
        g.begin(ShapeRenderer.ShapeType.Line);
        int zoomDiameter = (int) radius * 2;
        g.setColor(Color.YELLOW);
        if(zoomDiameter >= 2)
        {
            g.setColor(Color.BLACK);
            g.circle((int)(drawX), (int)(drawY), zoomDiameter / 2.0f);
            g.setColor(Color.WHITE);
            g.circle((int)(drawX-1), (int)(drawY-1), (int)(zoomDiameter / 2.0f)+2);
        }
        else
        {
            g.setColor(Color.BLACK);
            g.circle((int)(drawX), (int)(drawY), 2);
            g.setColor(Color.WHITE);
            g.circle((int)(drawX-1), (int)(drawY-1), (int)2);
        }

        g.end();

        if(handler.getApplication().debugMode)
        {
            drawDebugVectors(g);
            handler.getApplication().getSpriteBatch().begin();
            handler.getApplication().getSpriteBatch().setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
            handler.getApplication().getFont().setColor(Color.WHITE);
            handler.getApplication().getFont().getData().setScale(handler.getApplication().getCamera().getZoomLevel() / 9.0f + (8.0f / 9.0f));
            handler.getApplication().getFont().draw(handler.getApplication().getSpriteBatch(),mass+" Mg",(int)(drawX), (int)(drawY));
            handler.getApplication().getFont().draw (handler.getApplication().getSpriteBatch(),velocity.getMagnitude()+" km/s",(int)(drawX), (int)(drawY + (20 * (handler.getApplication().getCamera().getZoomLevel() / 9.0f + (8.0f / 9.0f)))));
            handler.getApplication().getFont().draw (handler.getApplication().getSpriteBatch(),netForce.getMagnitude()+" kN",(int)(drawX), (int)(drawY + (40 * (handler.getApplication().getCamera().getZoomLevel() / 9.0f + (8.0f / 9.0f)))));
            handler.getApplication().getSpriteBatch().end();
        }
    }

    public void drawDebugVectors(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Line);
        g.setColor(Color.RED);
        g.line((int)drawXCenter, (int)drawYCenter, (int)drawXCenter + (int)(this.velocity.getX()*40), (int)drawYCenter + (int)(this.velocity.getY()*40));
        g.setColor(Color.GREEN);
        g.line((int)drawXCenter, (int)drawYCenter, (int)drawXCenter + (int)(this.netForce.getX()*40),(int)drawYCenter + (int)(this.netForce.getY()*40));
        g.end();
    }
}
