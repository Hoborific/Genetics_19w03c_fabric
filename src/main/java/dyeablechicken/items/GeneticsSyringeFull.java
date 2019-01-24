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
                list_1.add(new StringTextComponent(("Genes: " + Arrays.toString(itemStack_1.getTag().getIntArray("dyeablechicken:genes")))));
            } else
                list_1.add(new StringTextComponent("Empty Syringe"));
        }
    }

}
