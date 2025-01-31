package com.planetsim.universe;


import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.game.Handler;
import com.planetsim.game.PlanetSim;
import com.planetsim.game.Textures;

//the universe the sim is in
//holds all the entities / does updates on their movements.
public class Universe
{
    //CHANGE THIS TO DETERMINE HOW FAST OBJECTS MOVE
    public float GRAVITATIONAL_CONSTANT = 6.674f; //Higher number = faster but more inaccurate simulation. Lower number = slower but more accurate.

    private Handler handler;
    private ArrayList<Entity> entities;
    public static int trailSize = 200;


    private SpriteBatch batch;

    int[] r1;
    int[] r2;
    int[] r3;
    float[] c;


    public Universe(Handler handler)
    {
        this.handler = handler;
        this.entities = new ArrayList<Entity>();

        batch = new SpriteBatch();

        r1 = new int[800];
        r2 = new int[800];
        r3 = new int[800];
        c = new float[800];

        for(int i = 0; i < 800; i++){
            r1[i] = (int)(Math.random() * handler.getWidth());
            r2[i] = (int)(Math.random() * handler.getHeight());
            r3[i] = (int)(Math.random() * 1) + 1;
            c[i] = (float)Math.random();
        }
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
    }

    public ArrayList<Entity> getEntities()
    {
        return this.entities;
    }

    public void reset()
    {
        this.entities.clear();
        handler.getCamera().cameraReset();
        GRAVITATIONAL_CONSTANT = 6.674f;
        trailSize = 200;
    }

    public void tick()
    {
        //collisionDetection();
        for(int i = 0; i < entities.size(); i++)
            entities.get(i).tick();
    }

    public void render(ShapeRenderer g)
    {
        //filling in background
        drawBackground(g);

        //rendering entities
        for(int i = 0; i < entities.size(); i++)
            //if the planet is on screen, then render it
            if(entities.get(i).isVisible())
                entities.get(i).render(g);
    }

    public void drawBackground(ShapeRenderer g)
    {
        batch.begin();
        batch.setProjectionMatrix(handler.getApplication().getDefaultProjection());
        batch.draw(Textures.background, 0, 0, handler.getWidth(), handler.getHeight());
        batch.end();

        switch((int)(Math.random() * 2)){
            case 0:
                c[(int)(Math.random() * 799)] -= 0.2f;
                c[(int)(Math.random() * 799)] -= 0.2f;
                c[(int)(Math.random() * 799)] -= 0.2f;
                c[(int)(Math.random() * 799)] -= 0.2f;
                c[(int)(Math.random() * 799)] -= 0.2f;
                break;
            case 1:
                c[(int)(Math.random() * 799)] += 0.2f;
                c[(int)(Math.random() * 799)] += 0.2f;
                c[(int)(Math.random() * 799)] += 0.2f;
                c[(int)(Math.random() * 799)] += 0.2f;
                c[(int)(Math.random() * 799)] += 0.2f;
                break;
            default:
                break;
        }


        g.begin(ShapeRenderer.ShapeType.Filled);
        g.setProjectionMatrix(handler.getApplication().getDefaultProjection());
        for (int i = 0; i < 800; i++) {
            g.setColor(c[i], c[i], c[i], 1);
            g.circle(r1[i] * (handler.getWidth() / (float) PlanetSim.initialWidth), r2[i] * (handler.getHeight() / (float) PlanetSim.initialHeight), r3[i]);
        }
        g.setProjectionMatrix(handler.getCamera().getCamera().combined);
        g.end();

        //g.begin(ShapeRenderer.ShapeType.Filled);
        //g.setColor(Color.BLACK);
        //g.rect(0, 0, handler.getWidth(), handler.getHeight());
        //g.end();
    }
}
