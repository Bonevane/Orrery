package com.planetsim.universe;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.game.Handler;
import com.badlogic.gdx.graphics.Color;
import com.planetsim.game.Textures;

public class Planet extends Entity
{
    protected int rand; //random direction for the AI
    protected int special;
    protected Color color;
    public Color color1;

    private SpriteBatch batch;
    public SpriteBatch shadow;
    public SpriteBatch light;

    float sunAngle;
    float sunAlpha;
    float sunDist;


    private ArrayList<Position> positions;

    public Planet(Handler handler, float x, float y, long mass)
    {
        super(handler, x, y, mass);
        color = new Color(handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f), 1);
        this.density = 20;
        calculateRadius(mass);
        this.positions = new ArrayList<Position>();
    }

    public Planet(Handler handler, float x, float y, long mass, Vector v)
    {
        super(handler, x, y, mass);
        color = new Color(handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f),handler.randomWithRange(0,255)/(255.0f), 1);
        this.density = 20;
        calculateRadius(mass);
        this.positions = new ArrayList<Position>();
        this.velocity = v;
        this.color1 = color;
        this.rand = (int)(Math.random() * 20) % 2;
        this.special = (int)(Math.random() * 200);

        batch = new SpriteBatch();
        shadow = new SpriteBatch();
        light = new SpriteBatch();

        sunAngle = 50;
        sunAlpha = 1.0f;
    }

    private void addPositions()
    {
        Position p = new Position(this.x, this.y);

        if(positions.size() < Universe.trailSize)
        {
            positions.add(p);
        }
        else
        {
            positions.remove(0);
            positions.add(p);
        }
    }

    public void removePositions(){
        for(int i = positions.size(); i > Universe.trailSize; i--){
            positions.remove(i-1);
        }
    }

    @Override
    public void tick() {
        calculateAttraction();
        if (!handler.getController().pausedGame)//just so the trail doesn't disappear while paused
        {
            addPositions();
            removePositions();
        }
    }

    @Override
    public void render(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Filled);
        //drawX / drawY effected by zoom
        int zoomDiameter = (int) radius * 2;//(radius/handler.getCamera().getZoomLevel())*2;
        g.setColor(color);
        if(zoomDiameter >= 2)
            g.circle((int)(drawX), (int)(drawY), zoomDiameter / 2.0f);
        else
            g.circle((int)(drawX), (int)(drawY), 2);

        if(handler.getController().drawTails)
            drawTail(g);
        g.end();

        //color1.a = 0.2f;
        if(special == 54){
            color.r += (float) Math.random() / 10.0f;
            color.g += (float) Math.random() / 10.0f;
            color.b += (float) Math.random() / 10.0f;
        }

        for(int i = 0; i < handler.getUniverse().getEntities().size(); i++){
            if(handler.getUniverse().getEntities().get(i).getClass().getSimpleName().equalsIgnoreCase("star")){
                sunAngle = -(float)Math.toDegrees(Math.atan(((handler.getUniverse().getEntities().get(i).drawXCenter - this.drawXCenter) / (handler.getUniverse().getEntities().get(i).drawYCenter - this.drawYCenter))));// + 10.0f;
                sunDist = (float)Math.sqrt((Math.pow((handler.getUniverse().getEntities().get(i).drawXCenter - Math.abs(this.drawXCenter)), 2.0) + Math.pow((handler.getUniverse().getEntities().get(i).drawYCenter - Math.abs(this.drawYCenter)), 2.0))) - (float)handler.getUniverse().getEntities().get(i).radius;

                if(handler.getUniverse().getEntities().get(i).y - this.y < 0){
                 sunAngle += 180;
                }
                System.out.println(handler.getUniverse().getEntities().get(i).radius);

                sunAlpha = (float)(handler.getUniverse().getEntities().get(i).radius / sunDist) * 3;
                System.out.println(sunAlpha);
                //break;
            }
        }



        batch.setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
        batch.begin();
        if(rand == 0)
            batch.draw(Textures.planetTex2, (int)(drawX - radius), (int)(drawY - radius), zoomDiameter, zoomDiameter);
        else
            batch.draw(Textures.planetTex, (int)(drawX - radius), (int)(drawY - radius), zoomDiameter, zoomDiameter);
        batch.end();


        // STYLE 2
        /*shadow.setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
        shadow.begin();
        //shadow.setColor(color1);
        //shadow.setColor(color.r + 0.3f, color.g + 0.3f, color.b + 0.3f, 0.4f);
        if(zoomDiameter >= 2){
            shadow.draw(Textures.shadowRegion, (float)(drawX - radius), (float)(drawY - radius), (float)(radius + 1), (float)(radius + 1), zoomDiameter, zoomDiameter, 1.0f, 1.0f, sunAngle);
        }
        else
            shadow.draw(Textures.shadowRegion, (int)(drawX - radius), (int)(drawY - radius), (int)(drawX), (int)(drawY), 2, 2, 1.0f, 1.0f, sunAngle);
        shadow.end();*/

        // STYLE 1
        light.setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
        light.begin();
        light.setColor(color.r + 0.4f, color.g + 0.4f, color.b + 0.4f, sunAlpha);
        light.draw(Textures.lightingRegion, (float)(drawX - radius), (float)(drawY - radius), (float)(radius), (float)(radius), zoomDiameter + 1, zoomDiameter + 1, 1.0f, 1.0f, sunAngle);
        light.end();
        sunAlpha = 0.2f;

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

    public void drawTail(ShapeRenderer g)
    {
        g.setColor(color);
        for(int i = 0; i < positions.size(); i++)
        {
            //if(i%5==0)
            //g.fillOval((int)positions.get(i).getX()-5, (int)positions.get(i).getY()-5, 10, 10);
            if(i != 0)
            {
                int drawX1 = (int)(positions.get(i).getX());// / handler.getCamera().getZoomLevel();
                int drawY1 = (int)(positions.get(i).getY());// / handler.getCamera().getZoomLevel();
                int drawX2 = (int)(positions.get(i-1).getX());// / handler.getCamera().getZoomLevel();
                int drawY2 = (int)(positions.get(i-1).getY());// / handler.getCamera().getZoomLevel();
                //if the line is on screen
                //if((drawX1<0 || drawX1>handler.getWidth()) || (drawY1<0 || drawY1>handler.getHeight()))
                    //continue;
                //else
                //{
                    //g.drawOval(drawX1-2, drawY1-2, 5, 5);
                g.rectLine(drawX1, drawY1, drawX2, drawY2, 2);// * handler.getCamera().getZoomLevel()  / 9.0f + (8.0f / 9.0f));
                //}
            }
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