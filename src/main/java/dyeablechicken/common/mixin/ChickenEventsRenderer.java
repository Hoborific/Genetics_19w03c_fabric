package dyeablechicken.common.mixin;

import dyeablechicken.client.LayerDyeableFeatureRenderer;
import dyeablechicken.common.IGeneticBase;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*Whole class is unused*/
@Mixin(LayerDyeableFeatureRenderer.class)
public abstract class ChickenEventsRenderer{

    @Inject(at = @At("HEAD"), method = "getColor", cancellable = true)
    public DyeColor getColor(ChickenEntity entity, CallbackInfoReturnable ret) {
        return DyeColor.byId(((IGeneticBase) entity).getGeneticsForPacket()[0]);
    }
}