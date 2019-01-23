package dyeablechicken.common.mixin;

import dyeablechicken.common.genetics.IGeneticBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dyeablechicken.util.Logger.log;


@Mixin(MooshroomEntity.class)
public class MooshroomEntityMixin {

    @Inject(at = @At("RETURN"), method = "createChild", cancellable = true)
    public void createChild(PassiveEntity passiveEntity, CallbackInfoReturnable<MooshroomEntity> muhFungus) {
        MooshroomEntity child =  muhFungus.getReturnValue();
        Entity e = (Entity)(Object) this;

        if (!e.world.isClient) {
            /* idk deliver babies or whatever */
            int [] parent1 = ((IGeneticBase)e).getGeneticsForPacket();
            int [] parent2 = ((IGeneticBase)passiveEntity).getGeneticsForPacket();


            ((IGeneticBase)child).setGeneticsFromPacket(((IGeneticBase)child).generateGenetics(parent1,parent2));

            log("in createChild");
        }

        muhFungus.setReturnValue(child); // if child is a new instance
    }

}