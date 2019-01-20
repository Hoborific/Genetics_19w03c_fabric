package dyeablechicken.client;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class RenderDyeableChicken extends MobEntityRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {//EntityMobRenderer<ChickenEntity,ChickenEntityModel<ChickenEntity>> {//EntityMobRenderer<ChickenEntity> {
    public static final Identifier CHICKEN_TEXTURES =   new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//"dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//new ResourceLocation("textures/entity/chicken.png");


    @Override
    protected float method_4045(ChickenEntity dyeableChickenEntity, float v) {
        //super.method_4045(dyeableChickenEntity, v);\
        float var3 = MathHelper.lerp(v, dyeableChickenEntity.field_6736, dyeableChickenEntity.field_6741);
        float var4 = MathHelper.lerp(v, dyeableChickenEntity.field_6738, dyeableChickenEntity.field_6743);
        return (MathHelper.sin(var3) + 1.0F) * var4;
    }

    public RenderDyeableChicken(EntityRenderDispatcher renderManagerIn, EntityRendererRegistry.Context context) {
        //super(renderManagerIn);
        super(renderManagerIn,new ChickenEntityModel<ChickenEntity>(),0.3f);
        //this.addFeature(new LayerDyeableFeatureRenderer(this));
        this.addFeature(new LayerDyeableFeatureRenderer(this));
        //super(renderManagerIn, new ChickenEntityModel(), 0.3F); //renderManagerIn =p_i47211_1_
        this.bindTexture(CHICKEN_TEXTURES);


    }

    @Override
    public void render(ChickenEntity entity, float x, float y, float z, float entityYaw, float partialTicks, float wtf) {
        super.render(entity, x, y, z, entityYaw, partialTicks,wtf);


    }


    protected void preRenderCallback(ChickenEntity entity, float f)
    {
        preRenderDyeableChicken(entity,f);
    } //LivingRenderer#4042?
    private void preRenderDyeableChicken(ChickenEntity entity, float f){

    }

    //@Override
    protected Identifier getTexture(ChickenEntity dyeableChickenEntity) {
        return CHICKEN_TEXTURES;
    }


}