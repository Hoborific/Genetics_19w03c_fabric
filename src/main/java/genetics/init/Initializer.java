package genetics.init;

import genetics.Main;
import genetics.items.GeneticsSyringeEmpty;
import genetics.items.GeneticsSyringeFull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Initializer {
    public static final Item SYRINGE_FULL = new GeneticsSyringeFull(new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));
    public static final Item SYRINGE_EMPTY = new GeneticsSyringeEmpty(new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(64));

    public static void init() {
        // Initialize Items

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "geneticssyringe_empty"), SYRINGE_EMPTY);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "geneticssyringe_full"), SYRINGE_FULL);

    }
}
