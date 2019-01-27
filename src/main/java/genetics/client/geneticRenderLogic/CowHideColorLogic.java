package genetics.client.geneticRenderLogic;

import net.minecraft.entity.Entity;

public class CowHideColorLogic extends BaseColorLogic {
    public CowHideColorLogic() {

    }

    public CowHideColorLogic(int primaryGeneticIndex) {
        super(primaryGeneticIndex);
    }

    public CowHideColorLogic(int primaryGeneticIndex, int secondaryGeneticIndex) {
        super(primaryGeneticIndex, secondaryGeneticIndex);
    }

    @Override
    public float[] geneticsToRGB(Entity en) {
        //float[] rgbAsFloat = intToRGBFloat((23 * getValueOfPrimaryGene(en)) + 23); // 23 base RGB, times 0-9 depending on genetic plus additional 23 to round towards 23-230 greyscale
        //System.out.println(rgbAsFloat[0] + " t" +rgbAsFloat[1] + " t"+rgbAsFloat[2] + " t");
        //return rgbAsFloat;
        int primary = getValueOfPrimaryGene(en);
        if (primary > 6) primary = 7;
        float test = 13 + (13 * primary);// + 23;
        int brown = (3 * primary);
        if (primary >= 7) {
            test += 98;
            brown = brown / 2;
        }
        float[] test2 = new float[]{(test + brown) / 255.0f, test / 255.0f, test / 255.0f};
        return test2;//System.out.println(Arrays.toString(test2));
    }


}
