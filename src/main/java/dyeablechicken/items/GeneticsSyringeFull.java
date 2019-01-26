package dyeablechicken.items;

import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

import static dyeablechicken.common.genetics.IGenetics.genomeSize;
import static dyeablechicken.util.Logger.*;

public class GeneticsSyringeFull extends GeneticsBaseItem {
    public GeneticsSyringeFull(Settings item$Settings_1) {
        super(item$Settings_1);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        ItemStack held = playerEntity_1.getMainHandStack();

        log("GeneticsSyringeFull use function. Genes: " + Arrays.toString(held.getTag().getIntArray("dyeablechicken:genes")));
        return super.use(world_1, playerEntity_1, hand_1);
    }

    @Override
    public void buildTooltip(ItemStack itemStack_1, World world_1, List<TextComponent> list_1, TooltipOptions tooltipOptions_1) {
        if (world_1 != null) {
            if (itemStack_1.hasTag()) {
                list_1.add(new StringTextComponent(("Animal Type: " + itemStack_1.getTag().getString("dyeablechicken:entitytype"))));

                list_1.add(new StringTextComponent("Chromosome 0, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene0"))));
                list_1.add(new StringTextComponent("Chromosome 1, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene1"))));
                list_1.add(new StringTextComponent("Chromosome 2, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene2"))));
                list_1.add(new StringTextComponent("Chromosome 3, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene3"))));
                list_1.add(new StringTextComponent("Chromosome 4, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene4"))));
                list_1.add(new StringTextComponent("Chromosome 5, Paternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:paternalGene5"))));

                list_1.add(new StringTextComponent("Chromosome 0, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene0"))));
                list_1.add(new StringTextComponent("Chromosome 1, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene1"))));
                list_1.add(new StringTextComponent("Chromosome 2, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene2"))));
                list_1.add(new StringTextComponent("Chromosome 3, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene3"))));
                list_1.add(new StringTextComponent("Chromosome 4, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene4"))));
                list_1.add(new StringTextComponent("Chromosome 5, Maternal: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:maternalGene5"))));
                }
            } else
                list_1.add(new StringTextComponent("Empty Syringe"));
        }
    }
