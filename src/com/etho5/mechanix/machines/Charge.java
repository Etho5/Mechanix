package com.etho5.mechanix.machines;

import com.etho5.mechanix.machines.cargo.Direction;

import java.io.Serializable;

public class Charge implements Serializable {

    private int magnitude;
    private Direction direction;

    public Charge(int magnitude, Direction direction) {
        this.magnitude = magnitude;
        this.direction = direction;
    }



    public int getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int magnitude) {
        this.magnitude = magnitude;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void nullify() {
        this.magnitude = 0;
        this.direction = Direction.UNSET;
    }
}
