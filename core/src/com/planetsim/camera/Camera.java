package com.planetsim.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.planetsim.game.PlanetSim;
import com.planetsim.universe.Universe;
import com.planetsim.game.Handler;

public class Camera implements java.io.Serializable
{
    private final int ZOOM_MAX = 1000;
    private final int ZOOM_MIN = 1;

    private PlanetSim app;
    private Handler handler;
    private float xOffset, yOffset;
    private int zoomLevel;
    private OrthographicCamera camera;
    public int planet;

    public Camera(PlanetSim app, Universe universe, Handler handler)
    {
        camera = new OrthographicCamera();
        this.app = app;
        this.handler = handler;
        this.xOffset = 0;
        this.yOffset = 0;
        this.zoomLevel = 1;
        this.planet = -1;
        camera.setToOrtho(false, app.getWidth(), app.getHeight());

    }

    public void move(float xAmt, float yAmt)
    {
        this.xOffset += xAmt * (zoomLevel / 9.0f + (8.0f / 9.0f));
        this.yOffset -= yAmt * (zoomLevel / 9.0f + (8.0f / 9.0f));
        camera.translate(xAmt * (zoomLevel / 9.0f + (8.0f / 9.0f)), -(yAmt * (zoomLevel / 9.0f + (8.0f / 9.0f))));
    }

    public void setPosition(float x, float y)
    {
        this.xOffset = x;
        this.yOffset = y;
        camera.position.set(xOffset, yOffset, 0);
    }

    public float getxOffset()
    {
        return xOffset;
    }

    public void setxOffset(float xOffset)
    {
        this.xOffset = xOffset;
    }

    public float getyOffset()
    {
        return yOffset;
    }

    public void setyOffset(float yOffset)
    {
        this.yOffset = yOffset;
    }

    public int getZoomLevel()
    {
        return this.zoomLevel;
    }

    public OrthographicCamera getCamera(){ return camera; }

    public float getXPos(){ return camera.position.x - ((camera.viewportWidth / 2) * (camera.zoom));}
    public float getYPos(){return camera.position.y + ((camera.viewportHeight / 2) * (camera.zoom));}


    public void setZoomLevel(int z)
    {
        if(z < ZOOM_MIN)
            z = ZOOM_MIN;
        if(z > ZOOM_MAX)
            z = ZOOM_MAX;
        if(zoomLevel < z)
            for(int i = 0; i <= z - zoomLevel; i++)
                zoomIn();
        else
        if(zoomLevel > z)
            for(int i = 0; i <= zoomLevel - z; i++)
                zoomOut();
    }

    //zooms towards the centre of the screen
    public void zoomIn()
    {
        if(zoomLevel > ZOOM_MIN)
        {
            //this.xOffset = (float) (xOffset) - ((app.getWidth()/2));
            //this.yOffset = (float) (yOffset) - ((app.getHeight()/2));
            camera.zoom -= 1.0f / 9.0f;
            this.zoomLevel--;
        }
    }

    //zooms away from the center of the screen
    public void zoomOut()
    {
        if(zoomLevel < ZOOM_MAX)
        {
            //this.xOffset = (float) (xOffset) + ((app.getWidth()/2));
            //this.yOffset = (float) (yOffset) + ((app.getHeight()/2));
            camera.zoom += 1.0f / 9.0f;
            this.zoomLevel++;
        }
    }

    public void zoomReset()
    {
        setZoomLevel(1);
    }

    public void setCamToOrbit(){
        if (planet > -1 && planet < handler.getUniverse().getEntities().size()){
            camera.position.set(handler.getUniverse().getEntities().get(planet).getX(), handler.getUniverse().getEntities().get(planet).getY(), 0);
            xOffset = handler.getUniverse().getEntities().get(planet).getDrawX() - (camera.viewportWidth / 2);
            yOffset = handler.getUniverse().getEntities().get(planet).getDrawY() - (camera.viewportHeight / 2);
        }
    }

    public void cameraReset()
    {
        this.xOffset = 0;
        this.yOffset = 0;
        this.zoomLevel = 1;
        camera.zoom = 1;
        this.planet = -1;
        camera.position.set((camera.viewportWidth / 2) - (camera.viewportWidth - PlanetSim.initialWidth) / 2, (camera.viewportHeight / 2) - (camera.viewportHeight - PlanetSim.initialHeight) / 2, 0);
    }
}
