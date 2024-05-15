package com.planetsim.universe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.planetsim.game.Handler;
import com.planetsim.game.Textures;

public class Star extends Entity
{
    SpriteBatch batch;

    public Star(Handler handler, float x, float y, long mass)
    {
        super(handler, x, y, mass);
        this.density = 40;
        calculateRadius(mass);
    }

    public Star(Handler handler, float x, float y, long mass, Vector v)
    {
        super(handler, x, y, mass);
        this.density = 40;
        calculateRadius(mass);
        this.velocity = v;

        batch = new SpriteBatch();

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
        //drawX / drawY effected by zoom
        g.setColor(Color.YELLOW);
        if((radius * 2) >= 2)
            g.circle((int)(drawX), (int)(drawY), (float)radius);
        else
            g.circle((int)(drawX), (int)(drawY), 2);

        g.end();

        batch.setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
        batch.begin();
        batch.draw(Textures.sunRegion, (int)(drawX - (radius * 1.2)), (int)(drawY - (radius * 1.2)), (int)((radius * 2) * 1.2), (int)((radius * 2) * 1.2));
        batch.end();

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
