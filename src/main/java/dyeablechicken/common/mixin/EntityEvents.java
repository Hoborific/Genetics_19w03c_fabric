package dyeablechicken.common.mixin;

import dyeablechicken.common.IGeneticBase;
import dyeablechicken.common.MyGenetics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Random;

import static dyeablechicken.util.Logger.log;


@Mixin(Entity.class)
public class EntityEvents implements IGeneticBase {
    @Shadow
    public World world;
    MyGenetics myGenes = new MyGenetics((Entity)(Object)this);
    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        initializeGenetics();
        tag.putString("dyeablechicken:genes", myGenes.getGenetics());
        tag.putBoolean("dyeablechicken:hasGenetics", myGenes.hasGenetics);
        log("Saved to tag Genetics " + myGenes.getGenetics());

    }

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    public void init(EntityType<?> entityType_1, World world_1, CallbackInfo ci) {

    }

    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        myGenes.setGenetics(tag.getString("dyeablechicken:genes"));
        myGenes.hasGenetics = tag.getBoolean("dyeablechicken:hasGenetics");
        log("Loaded from tag Genetics " + myGenes.getGenetics());
    }

    @Override
    public void initializeGenetics() {
        Random randy = new Random();
        if (myGenes.getGenetics().equals("[0, 1, 2]")){
            myGenes.setGenetics(Arrays.toString(new int[]{randy.nextInt(10), randy.nextInt(10), randy.nextInt(10)}));
            myGenes.hasGenetics = true;
            log("Initialized Genetics: " + myGenes.getGenetics());
        }
    }

    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public boolean interact(PlayerEntity playerEntity_1, Hand hand_1, CallbackInfoReturnable cir) {
        log("interact: " + myGenes.getClientGenetics());
        log(Integer.toString(myGenes.getEntityID()));
        return true;
    }
}
