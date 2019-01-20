package dyeablechicken;

import dyeablechicken.client.RenderDyeableChicken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		EntityRendererRegistry.INSTANCE.register(ChickenEntity.class, RenderDyeableChicken::new);
	}

}

