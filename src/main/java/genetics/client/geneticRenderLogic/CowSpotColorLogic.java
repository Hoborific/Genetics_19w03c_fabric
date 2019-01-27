package genetics.client.geneticRenderLogic;

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
        float greyscale = 13 + (13 * getValueOfPrimaryGene(en));
        int secondary = getValueofSecondaryGene(en);
        float brown = 1;
        if (secondary >= 4) { //apply brown spots to weak genes rather than greyscale (darker means brown now)
            brown = (float) (3 * secondary);
        }
        if (secondary >= 7) {
            greyscale += 100; //increase RGB base if strong genes
            brown = brown / 2; //half brown(RED) pigment to not tint the white
        }
        return new float[]{(greyscale + brown) / 255.0f, greyscale / 255.0f, greyscale / 255.0f};
    }
}
