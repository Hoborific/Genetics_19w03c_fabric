package dyeablechicken.common.mixin;

import dyeablechicken.common.genetics.Genome;
import dyeablechicken.common.genetics.IGenetics;
import dyeablechicken.init.Initializer;
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
import java.util.List;
import java.util.Random;

import static dyeablechicken.util.Logger.debugLog;
import static dyeablechicken.util.Logger.log;


@Mixin(Entity.class)
public class EntityGeneticEvents implements IGenetics {
    @Shadow
    public World world;
    Entity e = (Entity) (Object) this;
    Genome myGenes = new Genome((Entity) (Object) this);


    @Inject(at = @At("RETURN"), method = "toTag")
    public void toTag(CompoundTag tag, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {
                tag.putBoolean("dyeablechicken:hasgenes", myGenes.getHasGenes());
                tag.putIntArray("dyeablechicken:paternalGene0", myGenes.getGenes(0,true));
                tag.putIntArray("dyeablechicken:paternalGene1", myGenes.getGenes(1,true));
                tag.putIntArray("dyeablechicken:paternalGene2", myGenes.getGenes(2,true));
                tag.putIntArray("dyeablechicken:paternalGene3", myGenes.getGenes(3,true));
                tag.putIntArray("dyeablechicken:paternalGene4", myGenes.getGenes(4,true));
                tag.putIntArray("dyeablechicken:paternalGene5", myGenes.getGenes(5,true));
                tag.putIntArray("dyeablechicken:maternalGene0", myGenes.getGenes(0,false));
                tag.putIntArray("dyeablechicken:maternalGene1", myGenes.getGenes(1,false));
                tag.putIntArray("dyeablechicken:maternalGene2", myGenes.getGenes(2,false));
                tag.putIntArray("dyeablechicken:maternalGene3", myGenes.getGenes(3,false));
                tag.putIntArray("dyeablechicken:maternalGene4", myGenes.getGenes(4,false));
                tag.putIntArray("dyeablechicken:maternalGene5", myGenes.getGenes(5,false));
                debugLog("Saved Genetics to tag for Entity " + e.getEntityId());
            }
    }

    @Inject(at = @At("RETURN"), method = "<init>", cancellable = true)
    public void init(EntityType<?> entityType_1, World world_1, CallbackInfo ci) {
        if (e instanceof LivingEntity)

            if (e instanceof CowEntity) {
                initializeCowGenetics();
            } else {
                initializeGenetics();
            }
    }

    @Inject(at = @At("RETURN"), method = "fromTag", cancellable = true)
    public void fromTag(CompoundTag tag, CallbackInfo ci) {
        if (e instanceof LivingEntity)
            if (!world.isClient) {
                myGenes.setHasGenes(tag.getBoolean("dyeablechicken:hasgenes"));
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene0"), 0, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene1"), 1, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene2"), 2, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene3"), 3, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene4"), 4, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:paternalGene5"), 5, true);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene0"), 0, false);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene1"), 1, false);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene2"), 2, false);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene3"), 3, false);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene4"), 4, false);
                myGenes.setGenes(tag.getIntArray("dyeablechicken:maternalGene5"), 5, false);

                debugLog("Loaded Genes from tag for Entity " + e.getEntityId());
            }
    }

    public void initializeGenetics() {
        if (!world.isClient) {
            if (e instanceof LivingEntity) {
                debugLog("Initializing Genetics for Entity " + e.getEntityId());
                if (myGenes.getHasGenes() == false) {
                    debugLog("Generating Genetics for Entity " + e.getEntityId());
                    myGenes.generateGenes();
                } else
                    debugLog("Genetics for Entity " + e.getEntityId() + " already generated");
            }
        }
    }

    public void initializeCowGenetics() { // this should be in an enum or something not seperate functions, tie this to the entity type somehow
        if (!world.isClient) {
            if (myGenes.getHasGenes() == false) {
                myGenes.generateGenes();
                Random randy = new Random();
                int[] tempArray = new int[chromosomeSize];

                for (int i=0; i < chromosomeSize; i++) {
                    int temp = randy.nextInt(3) + randy.nextInt(3) + randy.nextInt(4); // weighted towards less than 5 instead of random 0-9

                    if (temp <= 1) {
                        temp = randy.nextInt(1);
                    }

                    tempArray[i] = temp;
                }

                myGenes.setGenes(tempArray,0,true);

                for (int i=0; i < chromosomeSize; i++) {
                    int temp = randy.nextInt(3) + randy.nextInt(3) + randy.nextInt(4); // weighted towards less than 5 instead of random 0-9

                    if (temp <= 1) {
                        temp = randy.nextInt(1);
                    }

                    tempArray[i] = temp;
                }

                myGenes.setGenes(tempArray,0,false);
            }

            log("Initialized Cow Genetics for Entity " + e.getEntityId());
        }
    }

    // Following function interferes with interaction behavior

    @Inject(at = @At("RETURN"), method = "interact", cancellable = true)
    public void interact(PlayerEntity playerEntity_1, Hand hand_1, CallbackInfoReturnable cir) {
        if (e instanceof LivingEntity) {
            StringBuilder logText = new StringBuilder();

            logText.append("Interacting with: " + e.getEntityId() + ", Genes: \n");
            logText.append("Has Genes: " + (myGenes.getHasGenes() ? "True" : "False"));

            for(int i=0; i < genomeSize; i++) {
                logText.append("Chromosome "+ i +", Paternal: " + Arrays.toString(myGenes.getGenes(i,true)) + "\n");
                logText.append("Chromosome "+ i +", Maternal: " + Arrays.toString(myGenes.getGenes(i,false)) + "\n");
            }

            logText.append("End Report.");

            log(logText.toString());

            if (!world.isClient) {
                ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
                if (itemStack_1.getItem() == Initializer.SYRINGE_EMPTY && !playerEntity_1.abilities.creativeMode) {
                    e.damage(DamageSource.GENERIC, 0.5f);
                    itemStack_1.subtractAmount(1);
                    ItemStack newSyringe = new ItemStack(Initializer.SYRINGE_FULL);
                    CompoundTag geneInfo = new CompoundTag();

                    geneInfo.putString("dyeablechicken:entitytype", e.getName().getString());
                    log("Setting entitytype to " + e.getName().getString());

                    geneInfo.putIntArray("dyeablechicken:paternalGene0", myGenes.getGenes(0,true));
                    log("Setting paternalGene0 to" + myGenes.getGenes(0,true));
                    geneInfo.putIntArray("dyeablechicken:paternalGene1", myGenes.getGenes(1,true));
                    log("Setting paternalGene1 to" + myGenes.getGenes(1,true));
                    geneInfo.putIntArray("dyeablechicken:paternalGene2", myGenes.getGenes(2,true));
                    log("Setting paternalGene2 to" + myGenes.getGenes(2,true));
                    geneInfo.putIntArray("dyeablechicken:paternalGene3", myGenes.getGenes(3,true));
                    log("Setting paternalGene3 to" + myGenes.getGenes(3,true));
                    geneInfo.putIntArray("dyeablechicken:paternalGene4", myGenes.getGenes(4,true));
                    log("Setting paternalGene4 to" + myGenes.getGenes(4,true));
                    geneInfo.putIntArray("dyeablechicken:paternalGene5", myGenes.getGenes(5,true));
                    log("Setting paternalGene5 to" + myGenes.getGenes(5,true));

                    geneInfo.putIntArray("dyeablechicken:maternalGene0", myGenes.getGenes(0,false));
                    geneInfo.putIntArray("dyeablechicken:maternalGene1", myGenes.getGenes(1,false));
                    geneInfo.putIntArray("dyeablechicken:maternalGene2", myGenes.getGenes(2,false));
                    geneInfo.putIntArray("dyeablechicken:maternalGene3", myGenes.getGenes(3,false));
                    geneInfo.putIntArray("dyeablechicken:maternalGene4", myGenes.getGenes(4,false));
                    geneInfo.putIntArray("dyeablechicken:maternalGene5", myGenes.getGenes(5,false));

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
    public int[] getGenes() {
        return myGenes.getGenes();
    }

    @Override
    public int[] getGenes(int index) {
        return myGenes.getGenes(index);
    }

    @Override
    public int[] getGenes(int index, boolean isFather) {
        return myGenes.getGenes(index, isFather);
    }

    @Override
    public int getGenes(int index, int position) {
        return myGenes.getGenes(index, position);
    }

    @Override
    public int getGenes(int index, int position, boolean isFather) {
        return myGenes.getGenes(index, position, isFather);
    }

    @Override
    public List<int[]> getGenome() {
        return myGenes.getGenome();
    }

    @Override
    public List<int[]> getGenome(boolean isFather) {
        return myGenes.getGenome(isFather);
    }

    @Override
    public void setGenes(List<int[]> newGenes) {
        myGenes.setGenes(newGenes);
    }

    @Override
    public void setGenes(List<int[]> newGenes, boolean isFather) {
        myGenes.setGenes(newGenes, isFather);
    }

    @Override
    public void setGenes(int[] newGenes, int chromosome, boolean isFather) {
        myGenes.setGenes(newGenes, chromosome, isFather);
    }

    @Override
    public void setGenes(int newGene, int chromosome, int position, boolean isFather) {
        myGenes.setGenes(newGene, chromosome, position, isFather);
    }

    @Override
    public void setGenesFromPacket(List<int[]> newGenes) {
        myGenes.setGenesFromPacket(newGenes);
    }

    @Override
    public void generateGenes() {
        myGenes.generateGenes();
    }

    @Override
    public void generateGenes(Entity fatherEntity, Entity motherEntity) {
        myGenes.generateGenes(fatherEntity, motherEntity);
    }

    @Override
    public void generateGenes(List<int[]> fatherGenes, List<int[]> motherGenes) {
        myGenes.generateGenes(fatherGenes, motherGenes);
    }

    @Override
    public List<int[]> generateHaploidGenes() {
        return myGenes.generateHaploidGenes();
    }

    @Override
    public boolean getHasParents() {
        return myGenes.getHasParents();
    }

    @Override
    public boolean getHasGenes() {
        return myGenes.getHasGenes();
    }

    @Override
    public void setHasParents(int parent1, int parent2) {
        myGenes.setHasParents(parent1, parent2);
    }

    @Override
    public int getParent(boolean isFather) {
        return myGenes.getParent(isFather);
    }

    @Override
    public void updateGenes() {
        myGenes.updateGenes();
    }

    @Override
    public void setHasGenes(boolean bool) {
        myGenes.setHasGenes(bool);
    }

}
