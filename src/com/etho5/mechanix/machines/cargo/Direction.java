package com.etho5.mechanix.machines.cargo;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.Arrays;
import java.util.List;

public enum Direction {

    POS_X(false),
    NEG_X(true),
    POS_Y(false),
    NEG_Y(true),
    POS_Z(false),
    NEG_Z(true);

    private final boolean negative;
    Direction(boolean negative) {
        this.negative = negative;
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
}
