package dyeablechicken.common.mixin;

import dyeablechicken.items.GeneticsSyringeFull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.xml.soap.Text;
import java.util.Arrays;
import java.util.List;

import static dyeablechicken.util.Logger.log;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Environment(EnvType.CLIENT)
    @Inject(method = "getTooltipText", at = @At("RETURN"))
    public List<TextComponent> getTooltipText(PlayerEntity var1, TooltipOptions var2, CallbackInfoReturnable<List<TextComponent>> info) {
        Item stack = ((ItemStack) (Object) this).getItem();
        if (stack instanceof GeneticsSyringeFull) {
            if (var1 != null) {
                List<TextComponent> list = info.getReturnValue();
                if (((GeneticsSyringeFull) stack).getEntityType() != null) {
                    list.add(new StringTextComponent("Animal Type: " + ((GeneticsSyringeFull) stack).getEntityType()));
                    int temp[] = ((GeneticsSyringeFull) stack).getGenes();
                    list.add(new StringTextComponent("Genes: " + Arrays.toString(temp)));
                }
                else
                    list.add(new StringTextComponent("Empty Syringe"));
            }
        }
        return info.getReturnValue();
    }
}