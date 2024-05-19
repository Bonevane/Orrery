package com.planetsim.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.planetsim.universe.*;
import com.planetsim.game.Handler;

public class Controller
{
    private Handler handler;
    private float x, y; //actual position in space
    private float lastMX, lastMY; //The last mouse position
    private float drawX, drawY; //where it's drawn at on screen
    Texture cursor;
    SpriteBatch batch;

    public boolean drawTails = false;
    public boolean pausedGame = false;

    private long planetMass = 20;
    private long starMass = 150;
    private long blackHoleMass = 500;

    private boolean placingPlanet = false;
    private boolean placingStar = false;
    private boolean placingBlackHole = false;
    private boolean placingAsteroid = false;

    public Controller(Handler handler)
    {
        this.handler = handler;
        this.drawTails = true;
        this.x = 0;
        this.y = 0;
        this.lastMX = 0;
        this.lastMY = 0;
        this.drawX = 0;
        this.drawY = 0;
        this.cursor = new Texture(Gdx.files.internal("cursor.png"));
        this.batch = new SpriteBatch();
    }

    private void getInput()
    {
        getMouseInput();
        getKeyboardInput();
    }

    private void getMouseInput()
    {
        this.drawX = handler.getKeyManager().mX;
        this.drawY = handler.getKeyManager().mY;
        this.x = this.drawX;//(this.drawX + handler.getCamera().getxOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        this.y = this.drawY;//(this.drawY + handler.getCamera().getyOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        float cX = handler.getKeyManager().cX;//(handler.getKeyManager().cX + handler.getCamera().getxOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        float cY = handler.getKeyManager().cY;//(handler.getKeyManager().cY + handler.getCamera().getyOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        float mX = handler.getKeyManager().mX;//(handler.getKeyManager().mX + handler.getCamera().getxOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        float mY = handler.getKeyManager().mY;//(handler.getKeyManager().mY + handler.getCamera().getyOffset() / handler.getCamera().getZoomLevel()) * handler.getCamera().getZoomLevel();
        float mXd = handler.getKeyManager().mXd;
        float mYd = handler.getKeyManager().mYd;

        //placing entities

        if(handler.getKeyManager().planet){
            placingPlanet = !placingPlanet;
            placingBlackHole = false;
            placingStar = false;
        }
        if(handler.getKeyManager().sun){
            placingStar = !placingStar;
            placingBlackHole = false;
            placingPlanet = false;
        }
        if(handler.getKeyManager().blackHole){
            placingBlackHole = !placingBlackHole;
            placingStar = false;
            placingPlanet = false;
        }


        if(handler.getKeyManager().leftClick){
            if (placingPlanet) {
                if (handler.getKeyManager().holding)
                    planetMass += planetMass * .05;
                if (!Gdx.input.isTouched()) {
                    Vector initialVector = new Vector((this.x - cX) / 20, (this.y - cY) / 20);
                    Planet tempPlanet = new Planet(handler, cX, cY, planetMass, initialVector);
                    handler.getUniverse().addEntity(tempPlanet);
                    handler.getKeyManager().leftClick = false;
                    planetMass = 20;
                    //placingPlanet = false;
                }
            }

            else if (placingStar) {
                if (handler.getKeyManager().holding)
                    starMass += starMass * .05;
                if (!Gdx.input.isTouched()) {
                    Vector initialVector = new Vector((this.x - cX) / 20, (this.y - cY) / 20);
                    Star tempStar = new Star(handler, cX, cY, starMass, initialVector);
                    handler.getUniverse().addEntity(tempStar);
                    handler.getKeyManager().leftClick = false;
                    starMass = 150;
                    //placingStar = false;
                }
            }

            else if (placingBlackHole) {
                if (handler.getKeyManager().holding)
                    blackHoleMass += blackHoleMass * .05;
                if (!Gdx.input.isTouched()) {
                    Vector initialVector = new Vector((this.x - cX) / 20, (this.y - cY) / 20);
                    BlackHole tempBlackHole = new BlackHole(handler, cX, cY, blackHoleMass, initialVector);
                    handler.getUniverse().addEntity(tempBlackHole);
                    handler.getKeyManager().leftClick = false;
                    blackHoleMass = 500;
                    //placingBlackHole = false;
                }
            }

            else if (handler.getKeyManager().dragging) {
                handler.getCamera().move((-Gdx.input.getDeltaX()), (-Gdx.input.getDeltaY()));
            }
        }

        if(handler.getKeyManager().mouseWheelUp)
        {
            handler.getCamera().zoomIn();
            handler.getKeyManager().mouseWheelUp = false;
        }

        if(handler.getKeyManager().mouseWheelDown)
        {
            handler.getCamera().zoomOut();
            handler.getKeyManager().mouseWheelDown = false;
        }

        this.lastMX = mX;
        this.lastMY = mY;
    }

    private void getKeyboardInput()
    {
        //Moving the camera
        //WASD KEYS
        if(handler.getCamera().planet < 0){
            if (handler.getKeyManager().up && !handler.getKeyManager().down)
                handler.getCamera().move(0, -10);
            if (handler.getKeyManager().down && !handler.getKeyManager().up)
                handler.getCamera().move(0, 10);
            if (handler.getKeyManager().left && !handler.getKeyManager().right)
                handler.getCamera().move(-10, 0);
            if (handler.getKeyManager().right && !handler.getKeyManager().left)
                handler.getCamera().move(10, 0);

            //ARROW KEYS
            if (handler.getKeyManager().upArrow && !handler.getKeyManager().downArrow)
                handler.getCamera().move(0, -1);
            if (handler.getKeyManager().downArrow && !handler.getKeyManager().upArrow)
                handler.getCamera().move(0, 1);
            if (handler.getKeyManager().leftArrow && !handler.getKeyManager().rightArrow)
                handler.getCamera().move(-1, 0);
            if (handler.getKeyManager().rightArrow && !handler.getKeyManager().leftArrow)
                handler.getCamera().move(1, 0);
        }

        if(handler.getKeyManager().debugMode) {
            handler.getApplication().debugMode = !handler.getApplication().debugMode;
            handler.getKeyManager().debugMode = false;
        }
        if(handler.getKeyManager().t) {
            this.drawTails = !drawTails;
            handler.getKeyManager().t = false;
        }
        if(handler.getKeyManager().p) {
            this.pausedGame = !pausedGame;
            handler.getKeyManager().p = false;
        }
        if(handler.getKeyManager().r) {
            handler.getUniverse().reset();
            handler.getKeyManager().r = false;
        }
        if(handler.getKeyManager().c) {
            handler.getCamera().cameraReset();
            handler.getKeyManager().c = false;
        }
        if(handler.getKeyManager().m) {
            if(!handler.getApplication().muted){
                handler.getApplication().bgm.setVolume(0);
                handler.getApplication().muted = true;
            }
            else{
                handler.getApplication().bgm.setVolume(100);
                handler.getApplication().muted = false;
            }

            handler.getKeyManager().m = false;
        }
        if(handler.getKeyManager().tab) {
            handler.getCamera().planet++;

            if(handler.getCamera().planet >= handler.getUniverse().getEntities().size())
                handler.getCamera().planet = -1;

            handler.getKeyManager().tab = false;
        }
        if(handler.getKeyManager().pgUp) {
            handler.getUniverse().GRAVITATIONAL_CONSTANT += 1.0f;
            handler.getKeyManager().pgUp = false;
        }
        if(handler.getKeyManager().pgDown) {
            handler.getUniverse().GRAVITATIONAL_CONSTANT -= 1.0f;

            if(handler.getUniverse().GRAVITATIONAL_CONSTANT < 1.0f)
                handler.getUniverse().GRAVITATIONAL_CONSTANT = 0.674f;

            handler.getKeyManager().pgDown = false;
        }
        if(handler.getKeyManager().incTrail) {
            Universe.trailSize += 10;
            handler.getKeyManager().incTrail = false;
        }
        if(handler.getKeyManager().decTrail) {
            Universe.trailSize -= 10;


            if(Universe.trailSize < 10)
                Universe.trailSize = 10;

            handler.getKeyManager().decTrail = false;
        }
        if(handler.getKeyManager().save) {
            handler.getApplication().saveFile.saveState();
            handler.getKeyManager().save = false;
        }
        if(handler.getKeyManager().load) {
            handler.getApplication().saveFile.loadState("save.txt");
            handler.getKeyManager().load = false;
        }
    }

    public void tick()
    {
        getInput();
    }

    public void render(ShapeRenderer g)
    {
        this.tick();

        /*batch.setProjectionMatrix(handler.getApplication().getCamera().getCamera().combined);
        batch.begin();
        batch.draw(cursor, handler.getKeyManager().mX, handler.getKeyManager().mY - (30 * (handler.getCamera().getZoomLevel()  / 9.0f + (8.0f / 9.0f))), 30 * (handler.getCamera().getZoomLevel()  / 9.0f + (8.0f / 9.0f)), 30 * (handler.getCamera().getZoomLevel()  / 9.0f + (8.0f / 9.0f)));
        batch.end();

        g.begin(ShapeRenderer.ShapeType.Filled);
        g.setColor(Color.RED);
        g.circle((int)drawX, (int)drawY, 10 * (handler.getCamera().getZoomLevel()  / 9.0f + (8.0f / 9.0f)));
        g.end();*/

        renderEntityPlacement(g);
    }

    private void renderEntityPlacement(ShapeRenderer g)
    {
        g.begin(ShapeRenderer.ShapeType.Line);
        g.setColor(Color.WHITE);
        if(handler.getKeyManager().leftClick) {
            if (placingPlanet) {
                //drawing mass
                double radius = ((Math.sqrt(Math.PI * planetMass / 20) * 10)); /// handler.getCamera().getZoomLevel())/2;
                g.circle((int) (handler.getKeyManager().cX), (int) (handler.getKeyManager().cY), (int) (radius));
                //drawing initial vector
                g.line(this.handler.getKeyManager().cX, this.handler.getKeyManager().cY, (int) this.drawX, (int) this.drawY);
            }

            if (placingStar) {
                //drawing mass
                double radius = (Math.sqrt(Math.PI * starMass / 40) * 10);// / handler.getCamera().getZoomLevel();
                g.circle((int) (handler.getKeyManager().cX), (int) (handler.getKeyManager().cY), (int) (radius));
                //drawing initial vector
                g.line(this.handler.getKeyManager().cX, this.handler.getKeyManager().cY, (int) this.drawX, (int) this.drawY);
            }

            if (placingBlackHole) {
                //drawing mass
                long radius = (long) (Math.sqrt(Math.PI * blackHoleMass / 500) * 10);// / handler.getCamera().getZoomLevel();
                g.circle((int) (handler.getKeyManager().cX), (int) (handler.getKeyManager().cY), (int) (radius));
                //drawing initial vector
                g.line(this.handler.getKeyManager().cX, this.handler.getKeyManager().cY, (int) this.drawX, (int) this.drawY);
            }
        }

        g.end();
    }
}