package dyeablechicken.common.mixin;

import dyeablechicken.common.IGeneticBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Random;

@Mixin(LivingEntity.class)
public class EntityEvents implements IGeneticBase {
    private int[] GENETIC_VALUES = IGeneticBase.GENETIC_VALUES;
    private boolean hasGenetics = false;

    @Inject(at = @At("RETURN"), method = "writeCustomDataToTag")
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        if(this.getGenetics() != null){
            tag.putIntArray("dyeablechicken:genes", this.GENETIC_VALUES);
            tag.putBoolean("dyeablechicken:hasGenetics",this.hasGenetics);
            System.out.println("Saved to tag Genetics " + Arrays.toString(this.GENETIC_VALUES));
        }else{
            System.out.println("not saving, no genetics");
        }

    }

    @Inject(at = @At("RETURN"), method = "initDataTracker")
    public void initDataTracker(CallbackInfo ci){
        initializeGenetics();
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromTag", cancellable = true)
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        this.GENETIC_VALUES = tag.getIntArray("dyeablechicken:genes");
        this.hasGenetics = tag.getBoolean("dyeablechicken:hasGenetics");
        System.out.println("Loaded Genetics " + Arrays.toString(this.GENETIC_VALUES));
    }

    @Override
    public void initializeGenetics() {
        Random randy = new Random();
        if(this.getGenetics() == null){
            this.GENETIC_VALUES = new int[]{randy.nextInt(10),randy.nextInt(10),randy.nextInt(10)};
            this.hasGenetics = true;
            System.out.println("INITIALIZED GENETICS: " + Arrays.toString( this.GENETIC_VALUES));
        }
    }

    @Override
    public int[] getGenetics() {
        return this.GENETIC_VALUES;
    }

    @Override
    public void setGenetics(int[] in) {
        this.GENETIC_VALUES = in;
    }
}
