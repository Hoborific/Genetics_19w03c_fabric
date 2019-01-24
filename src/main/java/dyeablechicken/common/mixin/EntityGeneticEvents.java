package dyeablechicken.common.mixin;

import com.sun.istack.internal.NotNull;
import dyeablechicken.common.genetics.IGeneSample;
import dyeablechicken.common.genetics.IGeneticBase;
import dyeablechicken.common.genetics.MyGenetics;
import dyeablechicken.init.Initializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
import java.util.Random;

import static dyeablechicken.util.Logger.debugLog;
import static dyeablechicken.util.Logger.log;


@Mixin(Entity.class)
public class EntityGeneticEvents implements IGeneticBase {
    @Shadow
    public World world;
    Entity e = (Entity)(Object)this;
    MyGenetics myGenes = new MyGenetics((Entity)(Object)this);


    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {
                tag.putIntArray("dyeablechicken:genes", myGenes.getGenetics());
                tag.putBoolean("dyeablechicken:hasGenetics", myGenes.hasGenetics);
                debugLog("Saved to tag Genetics " + Arrays.toString(myGenes.getGenetics()));
            }
    }

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    public void init(EntityType<?> entityType_1, World world_1, CallbackInfo ci) {
        if (e instanceof LivingEntity)
            initializeGenetics();
    }

    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {

                myGenes.setGenetics(tag.getIntArray("dyeablechicken:genes"));
                myGenes.hasGenetics = tag.getBoolean("dyeablechicken:hasGenetics");
                debugLog("Loaded from tag Genetics " + Arrays.toString(myGenes.getGenetics()));
            }
    }

    @Override
    public void initializeGenetics() {
        if (!world.isClient) {
            myGenes.setGenetics(generateGenetics());
            myGenes.hasGenetics = true;
            debugLog("Initialized Genetics: " + Arrays.toString(myGenes.getGenetics()));
        }
    }

    @Override
    public void initializeGenetics(int[] mum, int[] dad) {
        if (!world.isClient) {
            myGenes.setGenetics(generateGenetics(mum, dad));
            myGenes.hasGenetics = true;
            debugLog("Initialized Genetics: " + Arrays.toString(myGenes.getGenetics()));
        }
    }

    @Override
    public int[] generateGenetics(@NotNull int[] parent1, @NotNull int[] parent2) {
        int[] newGenetics = new int[genomeSize];

        if ((parent1.length < genomeSize) || (parent2.length < genomeSize)) {
            log("generateGenetics(int[], int[]) was passed arrays shorter than genomeSize, generating new random values.");
            return generateGenetics();
        }

        for (int i = 0; i < genomeSize; i++) {
            Random randy = new Random();

            if (randy.nextBoolean())
                newGenetics[i] = parent1[i];
            else
                newGenetics[i] = parent2[i];
        }

        return newGenetics;
    }

    @Override
    public int[] generateGenetics() {
        Random randy = new Random();
        int[] newGenetics = new int[genomeSize];

        for (int i = 0; i < genomeSize; i++) {
            newGenetics[i] = randy.nextInt(10);
        }

        return newGenetics;
    }

    @Override
    public void setGeneticsInherited(int[] arr) {
        myGenes.setGenetics(arr);
    }


    @Override
    public void setGeneticsFromPacket(int[] geneticarray) {
        debugLog("got genetics from packet ID: " + myGenes.getEntityID() + " " + Arrays.toString(geneticarray));
        this.myGenes.setGenetics(geneticarray);
    }

    @Override
    public int[] getGenetics() {
        if (myGenes.getGenetics().length < 2) {
            log("ERROR: ENTITY HAS NO GENETICS: " + myGenes.getEntityID() + " " + myGenes.getWorld().getEntityById(myGenes.getEntityID()).getClass().getCanonicalName());
            initializeGenetics();
            if (myGenes.getGenetics().length < 2) {
                log("ERROR: ENTITY STILL HAS NO GENETICS: " + myGenes.getEntityID() + " " + myGenes.getWorld().getEntityById(myGenes.getEntityID()).getClass().getCanonicalName());
                return new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            }
        }
        return myGenes.getGenetics();
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
                ((IGeneSample)newSyringe.getItem()).setGenes(myGenes.getGenetics());
                ((IGeneSample)newSyringe.getItem()).setEntityType(e.getName().getString());
                if (itemStack_1.isEmpty()) {
                    playerEntity_1.setStackInHand(hand_1, newSyringe);
                } else if (!playerEntity_1.inventory.insertStack(newSyringe)) {
                    playerEntity_1.dropItem(newSyringe, false);
                }

                cir.setReturnValue(true);
            }
            }
        }
        cir.setReturnValue(false);
    }

}
