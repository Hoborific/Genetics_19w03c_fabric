package dyeablechicken.common.genetics;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public interface IGeneSample {
    public int[] getGenes();
    public void setGenes(int[] genes);
    public String getEntityType();
    public void setEntityType(String e);
}
