package dyeablechicken.common.mixin;

import dyeablechicken.common.IGeneticBase;
import dyeablechicken.common.MyGenetics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Random;

@Mixin(Entity.class)
public class EntityEvents implements IGeneticBase {
    MyGenetics myGenes = new MyGenetics(this);

    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        if(myGenes.getGenetics() == null){
            initializeGenetics();
        }else if(myGenes.hasGenetics){
            tag.putIntArray("dyeablechicken:genes", myGenes.getGenetics());
            tag.putBoolean("dyeablechicken:hasGenetics",myGenes.hasGenetics);
            //System.out.println("Saved to tag Genetics " + Arrays.toString(myGenes.getGenetics()));
        }else if(myGenes.getGenetics() != null && !myGenes.hasGenetics){
            //System.out.println("not null, no genetics");
        }

    }

    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        if(tag.getBoolean("dyeablechicken:hasGenetics")){
            myGenes.setGenetics(tag.getIntArray("dyeablechicken:genes"));
            myGenes.hasGenetics = tag.getBoolean("dyeablechicken:hasGenetics");
            //System.out.println("Loaded from tag Genetics " + Arrays.toString(myGenes.getGenetics()));
        }else{
            //System.out.println("Nothing saved, skipping load");
        }
    }

    @Override
    public void initializeGenetics() {
        Random randy = new Random();
        if(myGenes.getGenetics() == null || myGenes.getGenetics().length < 2){
            myGenes.setGenetics(new int[]{randy.nextInt(10),randy.nextInt(10),randy.nextInt(10)});
            myGenes.hasGenetics = true;
            //System.out.println("INITIALIZED GENETICS: " + Arrays.toString( myGenes.getGenetics()));
        }
    }
    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public boolean interact(PlayerEntity playerEntity_1, Hand hand_1, CallbackInfoReturnable cir){
        if(!playerEntity_1.getEntityWorld().isClient()){
            System.out.println("interact: "+ Arrays.toString(myGenes.getGenetics()));
        }return true;
    }
    @Override
    public int[] getGenes() {
        return myGenes.getGenetics();
    }

    @Override
    public void setGenes(int[] in) {
        myGenes.setGenetics(in);
    }
}
