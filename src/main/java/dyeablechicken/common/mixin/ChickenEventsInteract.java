package dyeablechicken.common.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class ChickenEventsInteract {

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public boolean interactMob(PlayerEntity var1, Hand var2, CallbackInfoReturnable ret) {
        System.out.println("interact mixin called");
        return true;
    }
    public float[] getMyColor(Entity entity, CallbackInfo ci){
        return new float[]{1,1,1};
    }
}