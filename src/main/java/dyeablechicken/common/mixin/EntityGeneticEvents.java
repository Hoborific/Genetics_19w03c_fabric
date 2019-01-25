package dyeablechicken.common.mixin;

import com.sun.istack.internal.NotNull;
import dyeablechicken.Main;
import dyeablechicken.common.genetics.IGeneticBase;
import dyeablechicken.common.genetics.MyGenetics;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static dyeablechicken.util.Logger.debugLog;
import static dyeablechicken.util.Logger.log;


@Mixin(Entity.class)
public class EntityGeneticEvents implements IGeneticBase {
    @Shadow
    public World world;
    Entity e = (Entity) (Object) this;
    MyGenetics myGenes = new MyGenetics((Entity) (Object) this);


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
                for (int i = 0; i < Main.GENOMELENGTH; i++) {
                    myGenes.setGenetics(tag.getIntArray("dyeablechicken:genes" + i));
                }
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
    public void initializeGenetics(List<int[]> mum, List<int[]> dad) {
        if (!world.isClient) {
            myGenes.setGenetics(generateGenetics(mum, dad));
            myGenes.hasGenetics = true;
            debugLog("Initialized Genetics: " + Arrays.toString(myGenes.getGenetics()));
        }
    }

    public void initializeCowGenetics() { // this should be in an enum or something not seperate functions, tie this to the entity type somehow
        if (!world.isClient) {
            Random randy = new Random();
            int[] newGenetics = new int[genomeSize];

            for (int i = 0; i < genomeSize; i++) {
                if (i == 3) { // third index is hide color right now
                    int temp = randy.nextInt(3) + randy.nextInt(3) + randy.nextInt(4); // weighted towards less than 5 instead of random 0-9
                    System.out.println(temp);
                    if (temp <= 1) {
                        temp = randy.nextInt(1); // 50% chance of zero (black)
                    }
                    newGenetics[i] = temp;
                } else {
                    newGenetics[i] = randy.nextInt(10);
                }

            }
            myGenes.setGenetics(newGenetics);
            myGenes.hasGenetics = true;
            log("Initialized Cow Genetics: " + Arrays.toString(myGenes.getGenetics()));
        }
    }

    @Override
    public List<int[]> generateGenetics(@NotNull List<int[]> parent1, @NotNull List<int[]> parent2) {
        List<int[]> newGenetics = new ArrayList<int[]>();

        for (int i = 0; i < genomeSize; i++) {
            Random randy = new Random();
            int[] temp = new int[chromosomeSize];

            for (int j = 0; j < chromosomeSize; j++) {
                if (randy.nextBoolean())
                    temp[j] = parent1.get(i)[j];
                else
                    temp[j] = parent2.get(i)[j];
            }

            newGenetics.add(temp);
        }

        return newGenetics;
    }

    @Override
    public List<int[]> generateGenetics() {
        Random randy = new Random();
        List<int[]> newGenetics = new ArrayList<int[]>();

        for (int i = 0; i < genomeSize; i++) {
            int[] temp = new int[genomeSize];

            for (int j = 0; j < genomeSize; j++) {
                temp[i] = randy.nextInt(10);
            }

            newGenetics.add(temp);
        }
        return newGenetics;
    }

    @Override
    public int getGeneticsByIndex(int in) {
        return myGenes.getGeneticsByIndex(in);
    }

    @Override
    public int getGeneticsByIndex(int chromosome, int in) {
        return myGenes.getGeneticsByIndex(chromosome, in);
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

    @Override
    public int[] getGenetics(int chromosome) {
        return new int[0];
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
                    geneInfo.putString("dyeablechicken:entitytype", e.getName().getString());
                    geneInfo.putIntArray("dyeablechicken:genes", ((IGeneticBase)e).getGenetics());
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

}
