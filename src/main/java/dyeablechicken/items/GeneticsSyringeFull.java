package dyeablechicken.items;

import dyeablechicken.common.genetics.IGeneSample;
import dyeablechicken.common.genetics.IGeneticBase;
import dyeablechicken.common.genetics.MyGenetics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Arrays;

import static dyeablechicken.util.Logger.*;

public class GeneticsSyringeFull extends GeneSample {
    public GeneticsSyringeFull(Settings item$Settings_1) {
        super(item$Settings_1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        log("getUseAction genes: " + Arrays.toString(myGenes));
        return super.use(world_1, playerEntity_1, hand_1);
    }

    @Override
    public void setGenes(int[] genes) {
        myGenes = genes;


    }

    @Override
    public void setEntityType(String e) {
        entityType = e;
    }

}
