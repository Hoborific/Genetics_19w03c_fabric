package dyeablechicken.common.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dyeablechicken.common.net.PacketHandling.craftGeneticRequestPacket;
import static dyeablechicken.common.net.PacketHandling.craftGenomeRequestPacket;

@Mixin(World.class)
public class WorldEvents {

    @Inject(at = @At("RETURN"), method = "onEntityAdded")
    protected void onEntityAdded(Entity en, CallbackInfo ci) {
        if (en instanceof LivingEntity)
            if (en.world.isClient) {
                Packet pak = craftGenomeRequestPacket(en.getEntityId());
                en.world.sendPacket(pak);
            }
    }
}

