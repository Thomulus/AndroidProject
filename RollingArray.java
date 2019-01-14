package com.bigfoot.bigfoot;

public class RollingArray {
    public RollingArray(int maxSize){
        init(maxSize);
        arraySize = maxSize;
    }
    public native void init(int maxSize);
    public native void add(long barcode);
    public native int getNumOccurences(long barcode);

    private int arraySize;
    private long arrayAddress;
}
