package com.bigfoot.bigfoot;

public class GetBarcode {
    public GetBarcode(){
        init(); //initialize class in native cpp code
    };
    public native boolean barcodeMatch(long barcode);
    private native void init();
    public RollingArray ra;
}

