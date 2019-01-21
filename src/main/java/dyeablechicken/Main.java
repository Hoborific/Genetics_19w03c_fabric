package dyeablechicken;

import dyeablechicken.client.RenderDyeableChicken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.PacketByteBuf;

public class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		EntityRendererRegistry.INSTANCE.register(ChickenEntity.class, RenderDyeableChicken::new);
		TrackedDataHandlerRegistry.register(INTARRAY);
	}
	public static final TrackedDataHandler<int[]> INTARRAY = new TrackedDataHandler<int[]>() {
		public void write(PacketByteBuf packetByteBuf_1, int[] integer_1) {
			packetByteBuf_1.writeIntArray(integer_1);
		}

		public int[] read(PacketByteBuf packetByteBuf_1) {
			return packetByteBuf_1.readIntArray();
		}

		@Override
		public TrackedData<int[]> create(int i) {
			return new TrackedData(i,this);
		}

		public int[] copy(int[] integer_1) {
			return integer_1;
		}
	};


}

