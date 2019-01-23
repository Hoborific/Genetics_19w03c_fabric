package dyeablechicken.client;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GeneticChickenRenderer extends MobEntityRenderer<MobEntity, EntityModel<MobEntity>> {//EntityMobRenderer<ChickenEntity,ChickenEntityModel<ChickenEntity>> {//EntityMobRenderer<ChickenEntity> {
    public static final Identifier CHICKEN_TEXTURE = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//"dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//new ResourceLocation("textures/entity/chicken.png");
    public static final Identifier CHICKEN_LAYER_TEXTURE = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_coat.png");


    public GeneticChickenRenderer(EntityRenderDispatcher renderManagerIn, EntityRendererRegistry.Context context) {
        super(renderManagerIn, new ChickenEntityModel<>(), 0.3f);
        this.addFeature(new LayerDyeableFeatureRenderer(this, CHICKEN_LAYER_TEXTURE));
        this.bindTexture(CHICKEN_TEXTURE);
    }

    @Override
    protected float method_4045(MobEntity en, float v) {
        float var3 = MathHelper.lerp(v, ((ChickenEntity) en).field_6736, ((ChickenEntity) en).field_6741);
        float var4 = MathHelper.lerp(v, ((ChickenEntity) en).field_6738, ((ChickenEntity) en).field_6743);
        return (MathHelper.sin(var3) + 1.0F) * var4;
    }

    //@Override
    public void render(ChickenEntity entity, float x, float y, float z, float entityYaw, float partialTicks, float wtf) {
        super.render(entity, x, y, z, entityYaw, partialTicks,wtf);


    }
    //@Override
    protected Identifier getTexture(MobEntity dyeableChickenEntity) {
        return CHICKEN_TEXTURE;
    }


}