package dyeablechicken.common.mixin;

import com.sun.istack.internal.NotNull;
import dyeablechicken.common.genetics.IGeneticBase;
import dyeablechicken.common.genetics.MyGenetics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PassiveEntity;
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
    Entity e = (Entity)(Object)this;

    MyGenetics myGenes = new MyGenetics((Entity)(Object)this);
    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {
                tag.putIntArray("dyeablechicken:genes", myGenes.getGenetics());
                tag.putBoolean("dyeablechicken:hasGenetics", myGenes.hasGenetics);
                //log("Saved to tag Genetics " + Arrays.toString(myGenes.getGenetics()));
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
                //log("Loaded from tag Genetics " + Arrays.toString(myGenes.getGenetics()));
            }
    }

    @Override
    public void initializeGenetics() {
        if (!world.isClient) {
            if (e instanceof PassiveEntity)
                myGenes.setGenetics(generateGenetics());
                myGenes.hasGenetics = true;
            //log("Initialized Genetics: " + Arrays.toString(myGenes.getGenetics()));
        } else {

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
    public void setGeneticsFromPacket(int[] geneticarray) {
        log("got genetics from packet ID: " + myGenes.getEntityID() + " " + Arrays.toString(geneticarray));
        this.myGenes.setGenetics(geneticarray);
    }

    @Override
    public int[] getGeneticsForPacket() {
        if (myGenes.getGenetics().length < 2) {
            System.out.println(" ERROR: ENTITY HAS NO GENETICS: " + myGenes.getEntityID() + " " + myGenes.getWorld().getEntityById(myGenes.getEntityID()).getClass().getCanonicalName());
            initializeGenetics();
            if (myGenes.getGenetics().length < 2) {
                System.out.println(" ERROR: ENTITY STILL HAS NO GENETICS: " + myGenes.getEntityID() + " " + myGenes.getWorld().getEntityById(myGenes.getEntityID()).getClass().getCanonicalName());
                return new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            }
        }
        return myGenes.getGenetics();
    }

    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public boolean interact(PlayerEntity playerEntity_1, Hand hand_1, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity) {
            log("Interacting. Genes: " + Arrays.toString(myGenes.getGenetics()));
            log(Integer.toString(myGenes.getEntityID()));
            if (!world.isClient) {
                myGenes.setGenetics(myGenes.getGenetics());
            }
        }
        return true;
    }
}
