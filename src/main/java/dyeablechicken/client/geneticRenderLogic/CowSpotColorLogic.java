package dyeablechicken.client.geneticRenderLogic;

import net.minecraft.entity.Entity;

public class CowSpotColorLogic extends BaseColorLogic {
    public CowSpotColorLogic() {

    }

    public CowSpotColorLogic(int primaryGeneticIndex) {
        super(primaryGeneticIndex);
    }

    public CowSpotColorLogic(int primaryGeneticIndex, int secondaryGeneticIndex) {
        super(primaryGeneticIndex, secondaryGeneticIndex);
    }

    @Override
    public float[] geneticsToRGB(Entity en) {
        //float[] rgbAsFloat = intToRGBFloat(); // 30-255 greyscaled
        //System.out.println(rgbAsFloat[0] + " t" +rgbAsFloat[1] + " t"+rgbAsFloat[2] + " t");
        //return rgbAsFloat;
        float greyscale = 5 + (25 * getValueOfPrimaryGene(en)) + 25;
        return new float[]{greyscale / 255.0f, greyscale / 255.0f, greyscale / 255.0f};
    }
}
