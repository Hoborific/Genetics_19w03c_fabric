package dyeablechicken.client;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeneticCowRenderer extends MobEntityRenderer<MobEntity, EntityModel<MobEntity>> {//EntityMobRenderer<ChickenEntity,ChickenEntityModel<ChickenEntity>> {//EntityMobRenderer<ChickenEntity> {
    public static final Identifier COW_BASE = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//"dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//new ResourceLocation("textures/entity/chicken.png");
    public static final Identifier COW_LAYER_1 = new Identifier("dyeablechicken:textures/entity/dyeablechicken/chicken_coat.png");//"dyeablechicken:textures/entity/dyeablechicken/chicken_base.png");//new ResourceLocation("textures/entity/chicken.png");
    private Entity var1;


    public GeneticCowRenderer(EntityRenderDispatcher renderManagerIn, EntityRendererRegistry.Context context) {
        super(renderManagerIn, new CowEntityModel<>(), 0.3f);
        this.addFeature(new LayerDyeableFeatureRenderer(this, COW_LAYER_1));
        this.bindTexture(COW_BASE);

    }

    @Override
    public void render(MobEntity entity, float x, float y, float z, float entityYaw, float partialTicks, float wtf) {
        super.render(entity, x, y, z, entityYaw, partialTicks, wtf);
    }

    //@Override
    protected Identifier getTexture(MobEntity entity) {
        return COW_BASE;
    }

}