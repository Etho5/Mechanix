package com.etho5.mechanix.machines.cargo;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public enum Direction {

    UNSET(false),
    POS_X(false),
    NEG_X(true),
    POS_Y(false),
    NEG_Y(true),
    POS_Z(false),
    NEG_Z(true),
    NONE(false);

    private final boolean negative;
    Direction(boolean negative) {
        this.negative = negative;
    }

    public boolean isNegative() {
        return negative;
    }

    public static Block getBlockFrom(Block start, Direction dir, int distance) {
        Location l = new Location(start.getWorld(), start.getX(), start.getY(), start.getZ());
        switch(dir) {
            case POS_X:
            case NEG_X:
                if(dir.negative) {
                    l.setX(l.getX() - distance);
                } else l.setX(l.getX() + distance);
                break;
            case POS_Y:
            case NEG_Y:
                if(dir.negative) {
                    l.setY(l.getY() - distance);
                } else l.setY(l.getY() + distance);
                break;
            case POS_Z:
            case NEG_Z:
                if(dir.negative) {
                    l.setZ(l.getZ() - distance);
                } else l.setZ(l.getZ() + distance);
                break;
        }
        return start.getWorld().getBlockAt(l);
    }

    public Direction next() {
        List<Direction> dirs = Arrays.asList(Direction.values().clone());
        int pos = dirs.indexOf(this);
        try {
            return dirs.get(pos + 1);
        } catch(IndexOutOfBoundsException e) {
            return dirs.get(0);
        }
    }

    public static Direction getFromName(String name) {
        for(Direction d : Direction.values()) {
            if(d.name().equals(name)) return d;
        }
        return null;
    }

    public static boolean isOpposite(Direction one, Direction two) {
        switch(one) {
            case POS_X:
                return two == Direction.NEG_X;
            case NEG_X:
                return two == Direction.POS_X;
            case POS_Y:
                return two == Direction.NEG_Y;
            case NEG_Y:
                return two == Direction.POS_Y;
            case POS_Z:
                return two == Direction.NEG_Z;
            case NEG_Z:
                return two == Direction.POS_Z;
        }
        return false;
    }
}
