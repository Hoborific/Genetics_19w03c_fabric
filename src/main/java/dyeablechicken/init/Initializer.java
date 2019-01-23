package dyeablechicken.init;

import dyeablechicken.Main;
import dyeablechicken.items.GeneticsSyringe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Initializer {
    public static final Item SYRINGE = new GeneticsSyringe(new Item.Settings().itemGroup(ItemGroup.TOOLS));

    public static void init() {
        // Initialize Items

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "geneticssyringe"), SYRINGE);
    }
}
