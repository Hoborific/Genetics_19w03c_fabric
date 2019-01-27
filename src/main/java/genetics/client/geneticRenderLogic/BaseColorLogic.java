package genetics.client.geneticRenderLogic;

import genetics.common.genetics.IGeneticBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;

public class BaseColorLogic implements IColorLogic {

    int primaryGeneticIndex = 0;
    int secondaryGeneticIndex = 1;

    public BaseColorLogic() {

    }

    public BaseColorLogic(int primaryGeneticIndex) {
        this.primaryGeneticIndex = primaryGeneticIndex;
    }

    public BaseColorLogic(int primaryGeneticIndex, int secondaryGeneticIndex) {
        this.primaryGeneticIndex = primaryGeneticIndex;
        this.secondaryGeneticIndex = secondaryGeneticIndex;
    }

    public int getValueOfPrimaryGene(Entity en) {
        return ((IGeneticBase) en).getGeneticByIndex(primaryGeneticIndex);
    }

    public int getValueofSecondaryGene(Entity en) {
        return ((IGeneticBase) en).getGeneticByIndex(secondaryGeneticIndex);
    }

    public float[] geneticsToRGB(Entity en) {
        return SheepEntity.getRgbColor(DyeColor.byId((getValueOfPrimaryGene(en))));
    }
}
