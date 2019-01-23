package dyeablechicken.util;

public class ColorUtil {
    public void ColorUtil() {


    }

    public float[] toRGB(int in) {
        int R = (in & 16711680) >> 16;
        int G = (in & '\uff00') >> 8;
        int B = (in & 255); // >> 0
        return new float[]{R / 255.0F, G / 255.0F, B / 255.0F};
    }

    enum yamum {

    }
}
