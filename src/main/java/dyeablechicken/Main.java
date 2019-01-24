package dyeablechicken;

import dyeablechicken.client.render.GeneticChickenRenderer;
import dyeablechicken.client.render.GeneticCowRenderer;
import dyeablechicken.init.Initializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;

import static dyeablechicken.common.net.PacketHandling.*;

public class Main implements ModInitializer {

	public static final String MODID = "dyeablechicken";



	@Override
	public void onInitialize() {

		EntityRendererRegistry.INSTANCE.register(ChickenEntity.class, GeneticChickenRenderer::new);
		EntityRendererRegistry.INSTANCE.register(CowEntity.class, GeneticCowRenderer::new);

		CustomPayloadPacketRegistry.CLIENT.register(GENETIC_SYNC_PACKET, SYNC_PACKET_CONSUMER);
		CustomPayloadPacketRegistry.SERVER.register(GENETIC_REQUEST_PACKET, REQUEST_PACKET_CONSUMER);

        Initializer.init();
	}


}

