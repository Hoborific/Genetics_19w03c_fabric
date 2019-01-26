package dyeablechicken.common.mixin;

import dyeablechicken.common.genetics.IGenetics;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dyeablechicken.util.Logger.debugLog;


@Mixin({
        ChickenEntity.class, CatEntity.class, CowEntity.class,
        DonkeyEntity.class, HorseEntity.class, LlamaEntity.class,
        MooshroomEntity.class, OcelotEntity.class, PandaEntity.class,
        PandaEntity.class, ParrotEntity.class, PigEntity.class,
        PolarBearEntity.class, RabbitEntity.class, SheepEntity.class,
        TurtleEntity.class, WolfEntity.class
})//turtle has an egg class and probably doesn't work
public class GenericEntityMixin {

    @Inject(at = @At("RETURN"), method = "createChild", cancellable = true)
    public void createChild(PassiveEntity passiveEntity, CallbackInfoReturnable<LivingEntity> cir) {
        LivingEntity child = cir.getReturnValue();
        Entity e = (Entity)(Object) this;

        if (!e.world.isClient) {
            /* idk deliver babies or whatever */
            ((IGenetics) child).generateGenes(e, passiveEntity);
            debugLog("Set Geneties for Child Entity");
        }
        cir.setReturnValue(child); // if child is a new instance
    }

}
