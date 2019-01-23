package dyeablechicken.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dyeablechicken.common.genetics.IGeneticBase;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;


public class LayerDyeableFeatureRenderer extends FeatureRenderer<MobEntity, EntityModel<MobEntity>> {
    private final EntityModel<MobEntity> myModel;
    private final Identifier LAYER_TEXTURES;
    int geneticToTrack;

    LayerDyeableFeatureRenderer(FeatureRendererContext<MobEntity, EntityModel<MobEntity>> var1, Identifier texture, int geneIndex) {
        super(var1);
        myModel = var1.getModel();
        LAYER_TEXTURES = texture;
        geneticToTrack = geneIndex;


    }

    private DyeColor getColor(Entity entity) {
        return DyeColor.byId(((IGeneticBase) entity).getGenetics()[geneticToTrack]);
    }

    @Override
    public void render(MobEntity en, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.bindTexture(LAYER_TEXTURES);
        float[] afloat = SheepEntity.getRgbColor(this.getColor(en));
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