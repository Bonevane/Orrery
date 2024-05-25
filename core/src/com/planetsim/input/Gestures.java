package com.planetsim.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.planetsim.game.Handler;

public class Gestures extends InputAdapter implements GestureListener {
    private Handler handler;

    public Gestures(Handler handler){
        this.handler = handler;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        if (originalDistance >= currentDistance) {
            handler.getCamera().zoomOut();
        } else {
            handler.getCamera().zoomIn();
        }
        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
        return false;
    }

    @Override
    public void pinchStop () {
    }

    @Override
    public boolean scrolled(float amountX, float amountY){
        if(amountX > 0 || amountY > 0){
            handler.getCamera().zoomOut();
        }
        else if (amountX < 0 || amountY < 0){
            handler.getCamera().zoomIn();
        }

        return false;
    }
}
