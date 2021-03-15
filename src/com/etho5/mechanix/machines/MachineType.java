package com.etho5.mechanix.machines;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public enum MachineType {

    @SerializedName("Machine")
    MACHINE(45, new int[]{20, 21}, new int[]{14, 15, 16, 23, 24, 25, 32, 33, 34}, 14, 15, 16, 20, 21, 23, 24, 25, 32, 33, 34),
    @SerializedName("Generator")
    GENERATOR(45, new int[]{13, 14, 15, 22, 23, 24, 31, 32, 33}, null, 13, 14, 15, 22, 23, 24, 31, 32, 33),
    @SerializedName("Wire")
    WIRE(27, null, null, -1),
    @SerializedName("Cargo")
    CARGO(45,null, new int[]{13, 14, 15, 16, 22, 23, 24, 25, 31, 32, 33, 34}, 13, 14, 15, 16, 22, 23, 24, 25, 31, 32, 33, 34);

    private final int size;
    private final int[] clickableSlots, input;
    private int[] outputOrWhitelist;
    MachineType(int size, int[] input, int[] outputOrWhitelist, int... clickableSlots) {
        this.size = size;
        this.clickableSlots = clickableSlots;
        this.input = input;
        this.outputOrWhitelist = outputOrWhitelist;
    }

    public List<Integer> getClickableSlotList() {
        List<Integer> l = new ArrayList<>();
        for(int x : this.clickableSlots) l.add(x);
        return l;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * @return input slots
     */
    public int[] getInput() {
        return this.input;
    }

    /**
     * @return whitelist slots if cargo, output slots otherwise
     */
    public int[] getOutputOrWhitelist() {
        return this.outputOrWhitelist;
    }

    public void setOutputOrWhitelist(int[] outputOrWhitelist) {
        this.outputOrWhitelist = outputOrWhitelist;
    }
}
