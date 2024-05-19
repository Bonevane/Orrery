package com.planetsim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Textures{
    public static Texture background;
    public static Texture planetTex;
    public static Texture planetTex2;
    public static Texture lighting;
    public static Texture shadow;
    public static Texture sun;
    public static Texture mute;
    public static Texture pause;
    public static TextureRegion shadowRegion;
    public static TextureRegion lightingRegion;
    public static TextureRegion sunRegion;


    public static Texture zoomOut;
    public static TextureRegion zoomOutRegion;
    public static TextureRegionDrawable zoomOutDrawable;
    public static Texture zoomIn;
    public static TextureRegion zoomInRegion;
    public static TextureRegionDrawable zoomInDrawable;
    public static Texture setCamera;
    public static TextureRegion setCameraRegion;
    public static TextureRegionDrawable setCameraDrawable;
    public static Texture debug;
    public static TextureRegion debugRegion;
    public static TextureRegionDrawable debugDrawable;
    public static Texture trail;
    public static TextureRegion trailRegion;
    public static TextureRegionDrawable trailDrawable;
    public static Texture muteB;
    public static TextureRegion muteBRegion;
    public static TextureRegionDrawable muteBDrawable;
    public static Texture reset;
    public static TextureRegion resetRegion;
    public static TextureRegionDrawable resetDrawable;
    public static Texture resetCam;
    public static TextureRegion resetCamRegion;
    public static TextureRegionDrawable resetCamDrawable;
    public static Texture planetB;
    public static TextureRegion planetBRegion;
    public static TextureRegionDrawable planetBDrawable;
    public static Texture sunB;
    public static TextureRegion sunBRegion;
    public static TextureRegionDrawable sunBDrawable;
    public static Texture holeB;
    public static TextureRegion holeBRegion;
    public static TextureRegionDrawable holeBDrawable;

    Textures() {
        background = new Texture(Gdx.files.internal("background.jpg"));
        planetTex = new Texture(Gdx.files.internal("planet.png"));
        planetTex2 = new Texture(Gdx.files.internal("planet2.png"));
        lighting = new Texture(Gdx.files.internal("lighting.png"));
        shadow = new Texture(Gdx.files.internal("shadow.png"));
        sun = new Texture(Gdx.files.internal("sun.png"));
        mute = new Texture(Gdx.files.internal("mute.png"));
        pause = new Texture(Gdx.files.internal("paused.png"));
        shadowRegion = new TextureRegion(shadow, shadow.getWidth(), shadow.getHeight());
        lightingRegion = new TextureRegion(lighting, lighting.getWidth(), lighting.getHeight());
        sunRegion = new TextureRegion(sun, sun.getWidth(), sun.getHeight());


        zoomIn = new Texture(Gdx.files.internal("ZoomIn.png"));
        zoomInRegion = new TextureRegion(zoomIn);
        zoomInDrawable = new TextureRegionDrawable(zoomInRegion);

        zoomOut = new Texture(Gdx.files.internal("ZoomOut.png"));
        zoomOutRegion = new TextureRegion(zoomOut);
        zoomOutDrawable = new TextureRegionDrawable(zoomOutRegion);

        setCamera = new Texture(Gdx.files.internal("CameraOrbit.png"));
        setCameraRegion = new TextureRegion(setCamera);
        setCameraDrawable = new TextureRegionDrawable(setCameraRegion);

        debug = new Texture(Gdx.files.internal("DebugMenu.png"));
        debugRegion = new TextureRegion(debug);
        debugDrawable = new TextureRegionDrawable(debugRegion);

        trail = new Texture(Gdx.files.internal("TrailToggle.png"));
        trailRegion = new TextureRegion(trail);
        trailDrawable = new TextureRegionDrawable(trailRegion);

        muteB = new Texture(Gdx.files.internal("MuteButton.png"));
        muteBRegion = new TextureRegion(muteB);
        muteBDrawable = new TextureRegionDrawable(muteBRegion);

        reset = new Texture(Gdx.files.internal("ResetUniverse.png"));
        resetRegion = new TextureRegion(reset);
        resetDrawable = new TextureRegionDrawable(resetRegion);

        resetCam = new Texture(Gdx.files.internal("ResetCamera.png"));
        resetCamRegion = new TextureRegion(resetCam);
        resetCamDrawable = new TextureRegionDrawable(resetCamRegion);

        planetB = new Texture(Gdx.files.internal("AddPlanet.png"));
        planetBRegion = new TextureRegion(planetB);
        planetBDrawable = new TextureRegionDrawable(planetBRegion);

        sunB = new Texture(Gdx.files.internal("AddStar.png"));
        sunBRegion = new TextureRegion(sunB);
        sunBDrawable = new TextureRegionDrawable(sunBRegion);

        holeB = new Texture(Gdx.files.internal("AddBlackHole.png"));
        holeBRegion = new TextureRegion(holeB);
        holeBDrawable = new TextureRegionDrawable(holeBRegion);
    }
}
