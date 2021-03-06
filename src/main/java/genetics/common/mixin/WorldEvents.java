package genetics.common.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static genetics.common.net.PacketHandling.craftGeneticRequestPacket;

@Mixin(World.class)
public class WorldEvents {

    @Inject(at = @At("RETURN"), method = "onEntityAdded")
    protected void onEntityAdded(Entity en, CallbackInfo ci) {
        if (en instanceof LivingEntity)
            if (en.world.isClient) {
                Packet pak = craftGeneticRequestPacket(en.getEntityId());
                en.world.sendPacket(pak);
            }
    }
}

