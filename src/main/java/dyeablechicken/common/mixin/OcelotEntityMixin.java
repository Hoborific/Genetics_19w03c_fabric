package dyeablechicken.common.mixin;

import dyeablechicken.common.genetics.IGeneticBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dyeablechicken.util.Logger.log;


@Mixin(OcelotEntity.class)
public class OcelotEntityMixin {

    @Inject(at = @At("RETURN"), method = "createChild", cancellable = true)
    public void createChild(PassiveEntity passiveEntity, CallbackInfoReturnable<OcelotEntity> muhMeow) {
        OcelotEntity child =  muhMeow.getReturnValue();
        Entity e = (Entity)(Object) this;

        if (!e.world.isClient) {
            /* idk deliver babies or whatever */
            int [] parent1 = ((IGeneticBase)e).getGeneticsForPacket();
            int [] parent2 = ((IGeneticBase)passiveEntity).getGeneticsForPacket();


            ((IGeneticBase)child).setGeneticsFromPacket(((IGeneticBase)child).generateGenetics(parent1,parent2));

            log("in createChild");
        }

        muhMeow.setReturnValue(child); // if child is a new instance
    }

}