package com.planetsim.universe;

import com.badlogic.gdx.utils.Array;

public class Vector
{
    private double magnitude; // Overall Magnitued
    private double direction; // 0-360 (in degrees) in a circle around the entity
    private double x; // x value difference (magnitude in the x direction)
    private double y; //y value difference (magnitude in the y direction)

    public Vector()
    {
        this.magnitude = 0;
        this.direction = 0;
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
        calculateMagnitude();
        calculateDirection();
    }

    public double getMagnitude()
    {
        return this.magnitude;
    }

    public double getDirection()
    {
        return this.direction;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setX(double x)
    {
        this.x = x;
        calculateMagnitude();
        calculateDirection();
    }

    public void setY(double y)
    {
        this.y = y;
        calculateMagnitude();
        calculateDirection();
    }

    public void setVect(double x, double y)
    {
        this.x = x;
        this.y = y;
        calculateMagnitude();
        calculateDirection();
    }

    public void setDirection(double d)
    {
        this.direction = d;
        calculateXAndY();
        calculateMagnitude();
    }

    public void setMagnitude(double m)
    {
        this.magnitude = m;
        calculateXAndY();
        calculateDirection();
    }

    public void calculateXAndY()
    {
        this.y = Math.sin(Math.toRadians(this.direction)) * this.magnitude;
        this.x = Math.cos(Math.toRadians(this.direction)) * this.magnitude;
        this.y *= -1;
    }

    public void calculateDirection()
    {
        this.direction = Math.atan(this.x/(this.y));
    }

    public void calculateMagnitude()
    {
        this.magnitude = Math.sqrt((Math.pow(x, 2)) + (Math.pow(y, 2)));
    }

    public void add(double x, double y)
    {
        this.x += x;
        this.y += y;
        calculateMagnitude();
        calculateDirection();
    }

    public void add(Vector v)
    {
        this.x += v.getX();
        this.y += v.getY();
        calculateMagnitude();
        calculateDirection();
    }

    public void add(Array<Vector> v)
    {
        for(int i = 0; i < v.size; i++) {
            this.x += v.get(i).getX();
            this.y += v.get(i).getY();
        }
        calculateMagnitude();
        calculateDirection();
    }

    public void subtract(double x, double y)
    {
        this.x -= x;
        this.y -= y;
        calculateMagnitude();
        calculateDirection();
    }

    public void multiply(double x, double y)
    {
        this.x *= x;
        this.y *= y;
        calculateMagnitude();
        calculateDirection();
    }

    public void divide(double x, double y)
    {
        this.x /= x;
        this.y /= y;
        calculateMagnitude();
        calculateDirection();
    }

    public String toString()
    {
        return "x:"+this.x+", y:"+this.y+" | magnitude:"+this.magnitude+", direction:"+this.direction;
    }
}