package dyeablechicken.items;

import dyeablechicken.common.genetics.IGeneSample;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import java.util.Arrays;

import static dyeablechicken.util.Logger.debugLog;

public class GeneSample extends GeneticsBaseItem implements IGeneSample {
    protected int[] myGenes = new int[10];
    String entityType = null;

    Item thisItem = (Item)this;

    public GeneSample(Settings item$Settings_1) {
        super(item$Settings_1);
        setGenes(new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
    }

    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        tag.putIntArray("dyeablechicken:genes", getGenes());
        tag.putString("dyeablechicken:entitytype",getEntityType());
        debugLog("in GeneSample toTag: Saved to tag genes " + Arrays.toString(myGenes));
    }

    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        setGenes(tag.getIntArray("dyeablechicken:genes"));
        setEntityType(tag.getString("dyeablechicken:entitytype"));
        debugLog("Loaded from tag Genetics " + Arrays.toString(myGenes));
    }

    @Override
    public int[] getGenes() {
        return myGenes;
    }

    @Override
    public void setGenes(int[] genes) {
        myGenes = genes;

    }

    @Override
    public String getEntityType() {
        return entityType;
    }

    @Override
    public void setEntityType(String e) {
        entityType = e;
    }
}
