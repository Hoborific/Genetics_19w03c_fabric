package dyeablechicken.common.mixin;

import dyeablechicken.client.LayerDyeableFeatureRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LayerDyeableFeatureRenderer.class)
public abstract class ChickenEventsRenderer{

    @Inject(at = @At("HEAD"), method = "getColor", cancellable = true)
    public int getColor(ChickenEntity entity, CallbackInfoReturnable ret) {
        //log(entity.getAttributeContainer().get("color"));
        Random rand = new Random();
        return rand.nextInt(16);
    }
}