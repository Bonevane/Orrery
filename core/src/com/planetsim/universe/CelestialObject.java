package com.planetsim.universe;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.planetsim.game.Handler;

import java.util.ArrayList;

public class CelestialObject extends Entity
{
    protected int randDir; //random direction for the AI
    protected Color color;

    private ArrayList<Position> positions;

    public CelestialObject(Handler handler, float x, float y, long mass, double density)
    {
        super(handler, x, y, mass);
        color = new Color(handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f), 1);
        this.density = density;
        calculateRadius(mass);
        this.positions = new ArrayList<Position>();
    }

    public CelestialObject(Handler handler, float x, float y, long mass, double density, Vector v)
    {
        super(handler, x, y, mass);
        color = new Color(handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f), 1);
        this.density = density;
        calculateRadius(mass);
        this.positions = new ArrayList<Position>();
        this.velocity = v;
    }

    private void addPositions()
    {
        Position p = new Position(this.x, this.y);

        if(positions.size() < 60)
        {
            positions.add(p);
        }
        else
        {
            positions.remove(0);
            positions.add(p);
        }
    }

    @Override
    public void tick()
    {
        calculateAttraction();
        if(!handler.getController().pausedGame)//just so the trail doesnt disappear while paused
            addPositions();
    }

    @Override
    public void render(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Filled);
        //drawX / drawY effected by zoom
        int zoomRadius = (int) (radius/handler.getCamera().getZoomLevel())*2;
        g.setColor(color);
        if(zoomRadius >= 2)
            g.circle((int)(drawX), (int)(drawY), zoomRadius);
        else
            g.circle((int)(drawX), (int)(drawY), 2);

        if(handler.getController().drawTails)
            drawTail(g);

        if(handler.getApplication().debugMode)
        {
            drawDebugVectors(g);
            g.setColor(Color.WHITE);
            //g.drawString(mass+"",(int)(drawX), (int)(drawY));
            //g.drawString(velocity.getMagnitude()+"",(int)(drawX), (int)(drawY-10));
        }
        g.end();
    }

    public void drawTail(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Filled);
        g.setColor(color);
        for(int i = 0; i < positions.size(); i++)
        {
            //if(i%5==0)
            //g.fillOval((int)positions.get(i).getX()-5, (int)positions.get(i).getY()-5, 10, 10);
            if(i != 0)
            {
                int drawX1 = (int)(positions.get(i).getX()-handler.getCamera().getxOffset()) / handler.getCamera().getZoomLevel();
                int drawY1 = (int)(positions.get(i).getY()-handler.getCamera().getyOffset()) / handler.getCamera().getZoomLevel();
                int drawX2 = (int)(positions.get(i-1).getX()-handler.getCamera().getxOffset()) / handler.getCamera().getZoomLevel();
                int drawY2 = (int)(positions.get(i-1).getY()-handler.getCamera().getyOffset()) / handler.getCamera().getZoomLevel();
                //if the line is on screen
                if((drawX1<0 || drawX1>handler.getWidth()) || (drawY1<0 || drawY1>handler.getHeight()))
                    continue;
                else
                {
                    //g.drawOval(drawX1-2, drawY1-2, 5, 5);
                    g.line(drawX1, drawY1, drawX2, drawY2);
                }
            }
        }
        g.end();
    }

    public void drawDebugVectors(ShapeRenderer g)
    {
        g.setColor(Color.RED);
        g.line((int)drawXCenter, (int)drawYCenter, (int)drawXCenter + (int)(this.velocity.getX()*20), (int)drawYCenter + (int)(this.velocity.getY()*20));
        g.setColor(Color.GREEN);
        g.line((int)drawXCenter, (int)drawYCenter, (int)drawXCenter + (int)(this.netForce.getX()),(int)drawYCenter + (int)(this.netForce.getY()));
    }
}
