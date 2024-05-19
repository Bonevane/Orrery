package com.planetsim.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.planetsim.camera.Camera;
import com.planetsim.game.Handler;
import com.planetsim.game.PlanetSim;
import com.planetsim.game.Textures;

import static com.planetsim.game.PlanetSim.stage;

public class KeyManager{
    private Handler handler;
    public boolean up, down, left, right, shift, upArrow, downArrow, leftArrow, rightArrow, debugMode, leftClick, rightClick, middleClick, mouseWheelUp, mouseWheelDown, dragging;
    public boolean t, p, r, c, m, tab, esc, backspace, pgUp, pgDown, incTrail, decTrail, planet, sun, blackHole, save, load;
    public boolean holding, released, anyKeyPressed;
    public int mX,mY,cX,cY,dX,dY, mXd, mYd;
    private float alpha, temp;

    public Button zoomOutButton;
    public Button zoomInButton;
    public Button setCameraButton;
    public Button debugButton;
    public Button trailButton;
    public Button muteButton;
    public Button resetButton;
    public Button resetCamButton;
    public Button planetButton;
    public Button sunButton;
    public Button blackHoleButton;


    public KeyManager(Handler handler)
    {
        this.handler = handler;
        this.alpha = 0.0f;
        this.temp = 0.05f;
        initButtons();
        initListeners();

        stage.addActor(zoomInButton);
        stage.addActor(zoomOutButton);
        stage.addActor(setCameraButton);
        stage.addActor(debugButton);
        stage.addActor(resetButton);
        stage.addActor(resetCamButton);
        stage.addActor(muteButton);
        stage.addActor(trailButton);
        stage.addActor(planetButton);
        stage.addActor(sunButton);
        stage.addActor(blackHoleButton);
    }

    public void tick(Camera cam)
    {
        if(handler.getApplication().isMenu){
            if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                handler.getApplication().menuExit = true;
                handler.getApplication().menuEnter = false;
            }
        }

        up = Gdx.input.isKeyPressed(Input.Keys.W);
        down = Gdx.input.isKeyPressed(Input.Keys.S);
        left = Gdx.input.isKeyPressed(Input.Keys.A);
        right = Gdx.input.isKeyPressed(Input.Keys.D);
        shift = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
        upArrow = Gdx.input.isKeyPressed(Input.Keys.UP);
        downArrow = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        leftArrow = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        rightArrow = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        debugMode = Gdx.input.isKeyJustPressed(Input.Keys.F3);
        t = Gdx.input.isKeyJustPressed(Input.Keys.T);
        p = Gdx.input.isKeyJustPressed(Input.Keys.P);
        r = Gdx.input.isKeyJustPressed(Input.Keys.R);
        c = Gdx.input.isKeyJustPressed(Input.Keys.C);
        m = Gdx.input.isKeyJustPressed(Input.Keys.M);
        tab = Gdx.input.isKeyJustPressed(Input.Keys.TAB);
        esc = Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
        backspace = Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE);
        pgUp = Gdx.input.isKeyJustPressed(Input.Keys.PAGE_UP);
        pgDown = Gdx.input.isKeyJustPressed(Input.Keys.PAGE_DOWN);
        incTrail = Gdx.input.isKeyJustPressed(Input.Keys.O);
        decTrail = Gdx.input.isKeyJustPressed(Input.Keys.L);
        planet = Gdx.input.isKeyJustPressed(Input.Keys.NUM_1);
        sun = Gdx.input.isKeyJustPressed(Input.Keys.NUM_2);
        blackHole = Gdx.input.isKeyJustPressed(Input.Keys.NUM_3);
        save = Gdx.input.isKeyJustPressed(Input.Keys.NUM_9);
        load = Gdx.input.isKeyJustPressed(Input.Keys.NUM_0);

        mouseWheelUp = Gdx.input.isKeyPressed(Input.Keys.I);
        mouseWheelDown = Gdx.input.isKeyPressed(Input.Keys.K);

        mY = (int)((cam.getCamera().viewportHeight - ((Gdx.input.getY() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportHeight / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f)))) + cam.getyOffset())) - (int)(cam.getCamera().viewportHeight - PlanetSim.initialHeight) / 2;
        mX = (int)(((Gdx.input.getX() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportWidth / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f))) + cam.getxOffset())) - (int)(cam.getCamera().viewportWidth - PlanetSim.initialWidth) / 2;
        mYd = Gdx.input.getY();
        mXd = Gdx.input.getX();

        if(!zoomInButton.isPressed() && !zoomOutButton.isPressed() && !setCameraButton.isPressed() && !debugButton.isPressed() && !muteButton.isPressed() && !resetButton.isPressed() && !resetCamButton.isPressed() && !trailButton.isPressed() && !planetButton.isPressed() && !sunButton.isPressed() && !blackHoleButton.isPressed())
        {
            if (Gdx.input.justTouched()) {
                cY = (int) (cam.getCamera().viewportHeight - ((Gdx.input.getY() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportHeight / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f)))) + cam.getyOffset()) - (int) (cam.getCamera().viewportHeight - PlanetSim.initialHeight) / 2;
                cX = (int) ((Gdx.input.getX() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportWidth / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f))) + cam.getxOffset()) - (int) (cam.getCamera().viewportWidth - PlanetSim.initialWidth) / 2;
            }

            if (Gdx.input.isTouched()) {
                dragging = true;
                dY = (int) (cam.getCamera().viewportHeight - ((Gdx.input.getY() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportHeight / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f)))) + cam.getyOffset()) - (int) (cam.getCamera().viewportHeight - PlanetSim.initialHeight) / 2;
                dX = (int) ((Gdx.input.getX() * (cam.getZoomLevel() / 9.0f + (8.0f / 9.0f))) - ((cam.getCamera().viewportWidth / 2) * (cam.getZoomLevel() / 9.0f - (1.0f / 9.0f))) + cam.getxOffset()) - (int) (cam.getCamera().viewportWidth - PlanetSim.initialWidth) / 2;

                holding = true;
                released = false;

                leftClick = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
                rightClick = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
                middleClick = Gdx.input.isButtonPressed(Input.Buttons.MIDDLE);
            } else {
                dragging = false;
                holding = false;
                released = true;
                leftClick = false;
                rightClick = false;
                middleClick = false;
            }
        }


        if(alpha <= 0.99f && !handler.getApplication().menu && !handler.getApplication().isMenu){
            zoomInButton.setColor(1.0f,1.0f,1.0f,alpha);
            zoomOutButton.setColor(1.0f,1.0f,1.0f,alpha);
            setCameraButton.setColor(1.0f,1.0f,1.0f,alpha);
            debugButton.setColor(1.0f,1.0f,1.0f,alpha);
            trailButton.setColor(1.0f,1.0f,1.0f,alpha);
            muteButton.setColor(1.0f,1.0f,1.0f,alpha);
            planetButton.setColor(1.0f,1.0f,1.0f,alpha);
            sunButton.setColor(1.0f,1.0f,1.0f,alpha);
            blackHoleButton.setColor(1.0f,1.0f,1.0f,alpha);
            resetButton.setColor(1.0f,1.0f,1.0f,alpha);
            resetCamButton.setColor(1.0f,1.0f,1.0f,alpha);
            alpha += temp;
            temp /= 1.05f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    private void initButtons(){
        float scale = handler.getHeight() / 720.0f;
        float xPad = handler.getWidth() - (100 * scale);


        zoomInButton = new ImageButton(Textures.zoomInDrawable);
        zoomInButton.setPosition(xPad, handler.getHeight() - (100 * scale));
        zoomInButton.setColor(1.0f,1.0f,1.0f,alpha);
        zoomOutButton = new ImageButton(Textures.zoomOutDrawable);
        zoomOutButton.setPosition(xPad, handler.getHeight() - (150 * scale));
        zoomOutButton.setColor(1.0f,1.0f,1.0f, alpha);
        setCameraButton = new ImageButton(Textures.setCameraDrawable);
        setCameraButton.setPosition(xPad, handler.getHeight() - (230 * scale));
        setCameraButton.setColor(1.0f,1.0f,1.0f, alpha);
        debugButton = new ImageButton(Textures.debugDrawable);
        debugButton.setPosition(xPad, handler.getHeight() - (280 * scale));
        debugButton.setColor(1.0f,1.0f,1.0f, alpha);
        trailButton = new ImageButton(Textures.trailDrawable);
        trailButton.setPosition(xPad, handler.getHeight() - (330 * scale));
        trailButton.setColor(1.0f,1.0f,1.0f, alpha);
        muteButton = new ImageButton(Textures.muteBDrawable);
        muteButton.setPosition(xPad, handler.getHeight() - (380 * scale));
        muteButton.setColor(1.0f,1.0f,1.0f, alpha);

        //Object Buttons
        planetButton = new ImageButton(Textures.planetBDrawable);
        planetButton.setPosition(xPad, handler.getHeight() - (460 * scale));
        planetButton.setColor(1.0f,1.0f,1.0f, alpha);
        sunButton = new ImageButton(Textures.sunBDrawable);
        sunButton.setPosition(xPad, handler.getHeight() - (510 * scale));
        sunButton.setColor(1.0f,1.0f,1.0f, alpha);
        blackHoleButton = new ImageButton(Textures.holeBDrawable);
        blackHoleButton.setPosition(xPad, handler.getHeight() - (560 * scale));
        blackHoleButton.setColor(1.0f,1.0f,1.0f, alpha);


        resetButton = new ImageButton(Textures.resetDrawable);
        resetButton.setPosition(xPad, handler.getHeight() - (640 * scale));
        resetButton.setColor(1.0f,1.0f,1.0f, alpha);

        resetCamButton = new ImageButton(Textures.resetCamDrawable);
        resetCamButton.setPosition(xPad, handler.getHeight() - (690 * scale));
        resetCamButton.setColor(1.0f,1.0f,1.0f, alpha);


        zoomInButton.setSize(zoomInButton.getWidth() * scale, zoomInButton.getHeight() * scale);
        zoomOutButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        setCameraButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        debugButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        trailButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        muteButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        planetButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        sunButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        blackHoleButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        resetButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());
        resetCamButton.setSize(zoomInButton.getWidth(), zoomInButton.getHeight());

    }

    private void initListeners(){
        zoomOutButton.addListener(new ClickListener(){@Override
            public void clicked(InputEvent event, float x, float y) {handler.getCamera().zoomOut();}
        });

        zoomInButton.addListener(new ClickListener(){@Override
            public void clicked(InputEvent event, float x, float y) {handler.getCamera().zoomIn();}
        });

        setCameraButton.addListener(new ClickListener(){@Override
            public void clicked(InputEvent event, float x, float y) {
                handler.getCamera().planet++;
                if(handler.getCamera().planet >= handler.getUniverse().getEntities().size())
                    handler.getCamera().planet = -1;
            }
        });

        debugButton.addListener(new ClickListener(){@Override
            public void clicked(InputEvent event, float x, float y) {handler.getApplication().debugMode = !handler.getApplication().debugMode;}
        });

        trailButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {handler.getController().drawTails = !handler.getController().drawTails;}
        });

        muteButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {
            if(!handler.getApplication().muted) {
                handler.getApplication().bgm.setVolume(0);
                handler.getApplication().muted = true;
            } else {
                handler.getApplication().bgm.setVolume(100);
                handler.getApplication().muted = false;
            }
        }});

        resetButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {handler.getUniverse().reset();}
        });

        resetCamButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {handler.getCamera().cameraReset();}
        });

        planetButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {planet = true;}
        });

        sunButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {sun = true;}
        });

        blackHoleButton.addListener(new ClickListener(){@Override
        public void clicked(InputEvent event, float x, float y) {blackHole = true;}
        });
    }
}