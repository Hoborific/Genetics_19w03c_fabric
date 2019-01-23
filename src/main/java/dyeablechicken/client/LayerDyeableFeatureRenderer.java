package dyeablechicken.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dyeablechicken.common.genetics.IGeneticBase;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;


public class LayerDyeableFeatureRenderer extends FeatureRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
    private final ChickenEntityModel<ChickenEntity> chickenModel = new ChickenEntityModel();
    public static final Identifier CHICKEN_COAT_TEXTURES = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_coat.png"); //
    LayerDyeableFeatureRenderer(FeatureRendererContext<ChickenEntity, ChickenEntityModel<ChickenEntity>> var1) //RenderDyeableChicken dyeableChickenIn
    {super(var1); }

    private DyeColor getColor(ChickenEntity entity) {
        return DyeColor.byId(((IGeneticBase) entity).getGenetics()[0]);
    }

    @Override
    public void render(ChickenEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.bindTexture(CHICKEN_COAT_TEXTURES); //bindTexture

        float[] afloat = SheepEntity.getRgbColor(this.getColor(entitylivingbaseIn));
        GlStateManager.enableColorMaterial();
        GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
        this.getModel().method_17081(this.chickenModel);
        this.chickenModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean method_4200() { //shouldMergeTextures
        return false;
    }
}