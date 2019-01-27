package genetics.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import genetics.client.geneticRenderLogic.IColorLogic;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;


public class LayerDyeableFeatureRenderer extends FeatureRenderer<MobEntity, EntityModel<MobEntity>> {
    private final EntityModel<MobEntity> myModel;
    private final Identifier LAYER_TEXTURES;
    private IColorLogic myColorLogic;

    LayerDyeableFeatureRenderer(FeatureRendererContext<MobEntity, EntityModel<MobEntity>> var1, Identifier texture, IColorLogic whogivesahsit) {
        super(var1);
        myModel = var1.getModel();
        LAYER_TEXTURES = texture;
        myColorLogic = whogivesahsit;


    }
/*
    private DyeColor getColor(Entity entity) {
        return DyeColor.byId(((IGeneticBase) entity).getGenetics()[geneticToTrack]);
    }*/

    @Override
    public void render(MobEntity en, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.bindTexture(LAYER_TEXTURES);
        float[] afloat = myColorLogic.geneticsToRGB(en);
        GlStateManager.enableColorMaterial();
        GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
        this.getModel().method_17081(myModel);
        this.myModel.render(en, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean method_4200() { //shouldMergeTextures
        return false;
    }
}