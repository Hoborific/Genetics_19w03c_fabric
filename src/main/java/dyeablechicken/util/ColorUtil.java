package dyeablechicken.util;

public class ColorUtil {
    int BLACK = 2105376;
    int WHITE = 15263976;

    public static float[] intToRGBFloat(int in) {
        int R = (in & 16711680) >> 16;
        int G = (in & '\uff00') >> 8;
        int B = (in & 255);
        return new float[]{R / 255.0F, G / 255.0F, B / 255.0F};
    }

    public void ColorUtil() {

    }
}
