package dyeablechicken.client;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;


public class LayerDyeableFeatureRenderer extends FeatureRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
    private final ChickenEntityModel<ChickenEntity> chickenModel = new ChickenEntityModel();
    public static final Identifier CHICKEN_COAT_TEXTURES = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_coat.png"); //
    LayerDyeableFeatureRenderer(FeatureRendererContext<ChickenEntity, ChickenEntityModel<ChickenEntity>> var1) //RenderDyeableChicken dyeableChickenIn
    {super(var1); }

    private int getColor(ChickenEntity entity){
        return 5;
    }

    //@Override
    public void method_4199(ChickenEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

    }


    @Override
    public void render(ChickenEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.bindTexture(CHICKEN_COAT_TEXTURES); //bindTexture

        float[] afloat = DyeColor.byId(this.getColor(entitylivingbaseIn)).getColorComponents();//.getRgbColor(entitylivingbaseIn.getColor());
        GlStateManager.enableColorMaterial();
        if (true) { //RGB jeb code for testing render
            int var17 = 1;
            int var10 = entitylivingbaseIn.age / 25 + entitylivingbaseIn.getEntityId();
            int var11 = DyeColor.values().length;
            int var12 = var10 % var11;
            int var13 = (var10 + 1) % var11;
            float var14 = ((float)(entitylivingbaseIn.age % 25) + partialTicks) / 25.0F;
            float[] var15 = SheepEntity.getRgbColor(DyeColor.byId(var12));
            float[] var16 = SheepEntity.getRgbColor(DyeColor.byId(var13));
            GlStateManager.color3f(var15[0] * (1.0F - var14) + var16[0] * var14, var15[1] * (1.0F - var14) + var16[1] * var14, var15[2] * (1.0F - var14) + var16[2] * var14);
        }
        //GlStateManager.color3f(afloat[0],afloat[1],afloat[2]);
        entitylivingbaseIn.setCustomNameVisible(true);
        TextComponent text = new StringTextComponent( "Chicken_"  + ((entitylivingbaseIn.getHealth() / entitylivingbaseIn.getHealthMaximum()) * 100) + "%");
        entitylivingbaseIn.setCustomName(text);

        this.getModel().method_17081(this.chickenModel);
        //this.chickenModel.method_17080(entitylivingbaseIn,limbSwing,limbSwingAmount,partialTicks,netHeadYaw,headPitch,scale);
        this.chickenModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    @Override
    public boolean method_4200() { //shouldMergeTextures
        return false;
    }
}