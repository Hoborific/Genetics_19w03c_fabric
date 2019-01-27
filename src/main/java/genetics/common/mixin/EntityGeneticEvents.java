package genetics.common.mixin;

import genetics.common.genetics.BaseGenetics;
import genetics.common.genetics.CowGenetics;
import genetics.common.genetics.IGeneticBase;
import genetics.init.Initializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

import static genetics.util.Logger.debugLog;
import static genetics.util.Logger.log;


@Mixin(Entity.class)
public class EntityGeneticEvents implements IGeneticBase {
    @Shadow
    public World world;
    Entity e = (Entity) (Object) this;
    BaseGenetics myGenes = new BaseGenetics((Entity) (Object) this);


    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {
                tag.putIntArray("genetics:genes", myGenes.getGenetics());
                tag.putBoolean("genetics:hasGenetics", myGenes.hasGenetics);
                debugLog("Saved to tag Genetics " + Arrays.toString(myGenes.getGenetics()));
            }
    }

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    public void init(EntityType<?> entityType_1, World world_1, CallbackInfo ci) {
        if (e instanceof LivingEntity) {
            if (e instanceof CowEntity) {// this should probably not be in the event but in a function called by the event
                myGenes = new CowGenetics((Entity) (Object) this);
                myGenes.initializeGenetics();
            } else {
                myGenes.initializeGenetics();
            }
        }
    }
    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {

                myGenes.setGenetics(tag.getIntArray("genetics:genes"));
                myGenes.hasGenetics = tag.getBoolean("genetics:hasGenetics");
                debugLog("Loaded from tag Genetics " + Arrays.toString(myGenes.getGenetics()));
            }
    }

    // Following function interferes with interaction behavior

    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public void interact(PlayerEntity playerEntity_1, Hand hand_1, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity) {
            log("Interacting with: " + myGenes.getEntityID() + " Genes: " + Arrays.toString(myGenes.getGenetics()));
            if (!world.isClient) {
                //myGenes.setGenetics(myGenes.getGenetics());
                ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
                if (itemStack_1.getItem() == Initializer.SYRINGE_EMPTY && !playerEntity_1.abilities.creativeMode) {
                    e.damage(DamageSource.GENERIC, 0.5f);
                    itemStack_1.subtractAmount(1);
                    ItemStack newSyringe = new ItemStack(Initializer.SYRINGE_FULL);
                    CompoundTag geneInfo = new CompoundTag();
                    geneInfo.putString("genetics:entitytype", e.getName().getString());
                    geneInfo.putIntArray("genetics:genes", ((IGeneticBase) e).getGenetics());
                    newSyringe.setTag(geneInfo);
                    if (itemStack_1.isEmpty()) {
                        playerEntity_1.setStackInHand(hand_1, newSyringe);
                    } else if (!playerEntity_1.inventory.insertStack(newSyringe)) {
                        playerEntity_1.dropItem(newSyringe, false);
                    }
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Override
    public void initializeGenetics() {
        myGenes.initializeGenetics();
    }

    @Override
    public void initializeGenetics(int[] mum, int[] dad) {
        myGenes.initializeGenetics(mum, dad);
    }

    @Override
    public void setGeneticsFromPacket(int[] geneticArray) {
        myGenes.setGeneticsFromPacket(geneticArray);

    }

    @Override
    public int[] getGenetics() {
        return myGenes.getGenetics();
    }

    @Override
    public int[] generateGenetics(int[] parent1, int[] parent2) {
        return myGenes.generateGenetics(parent1, parent2);
    }

    @Override
    public int[] generateGenetics() {
        return myGenes.generateGenetics();
    }

    @Override
    public int getGeneticByIndex(int in) {
        return myGenes.getGeneticByIndex(in);
    }

    @Override
    public void setGeneticsInherited(int[] arr) {
        myGenes.setGeneticsInherited(arr);
    }
}
