package dyeablechicken.common.mixin;

import com.sun.istack.internal.Nullable;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import dyeablechicken.common.IGeneticBase;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;


@Mixin(ChickenEntity.class)



public class ChickenEvents implements IGeneticBase {
    int color = 3;
    int[] GENETIC_VALUES = IGeneticBase.GENETIC_VALUES;
    boolean hasGenetics = false;

    @Inject(at = @At("RETURN"), method = "writeCustomDataToTag", cancellable = true)
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        tag.putIntArray("dyeablechicken:genes", GENETIC_VALUES);
        tag.putBoolean("dyeablechicken:hasGenetics",hasGenetics);
        System.out.println("Saved Genetics");
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromTag", cancellable = true)
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        this.GENETIC_VALUES = tag.getIntArray("dyeablechicken:genes");
        this.hasGenetics = tag.getBoolean("dyeablechicken:hasGenetics");
        System.out.println("Loaded Genetics");
        System.out.println("Saved Genes: " + Arrays.toString(GENETIC_VALUES));
    }

    @Inject(at = @At("HEAD"), method = "createChild", cancellable = true)
    public ChickenEntity createChild(PassiveEntity var1, CallbackInfoReturnable ret) {
        return new ChickenEntity(var1.world);
    }

    @Inject(at = @At("HEAD"), method = "initAttributes", cancellable = true)
    public void initAttributes(CallbackInfo cb) {
        initializeGenetics();
    }
    @Override
    public void initializeGenetics() {
        Random randy = new Random();
        if(!hasGenetics){
            //GENETIC_VALUES = new int[]{randy.nextInt(10),randy.nextInt(10),randy.nextInt(10)};
        }
        System.out.println(Arrays.toString(GENETIC_VALUES));
    }

    @Override
    public int[] getGenetics() {
        return GENETIC_VALUES;
    }

    @Override
    public void setGenetics(int[] in) {
        GENETIC_VALUES = in;
    }
}

