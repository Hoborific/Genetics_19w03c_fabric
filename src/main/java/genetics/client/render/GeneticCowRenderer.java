package genetics.client.render;


import genetics.client.geneticRenderLogic.CowHideColorLogic;
import genetics.client.geneticRenderLogic.CowSpotColorLogic;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GeneticCowRenderer extends MobEntityRenderer<MobEntity, EntityModel<MobEntity>> {
    private static final Identifier COW_BASE = new Identifier("genetics:textures/entity/genetics/cow_base.png");
    private static final Identifier COW_LAYER_1 = new Identifier("genetics:textures/entity/genetics/cow_hide1.png");
    private static final Identifier COW_LAYER_2 = new Identifier("genetics:textures/entity/genetics/cow_spots3.png");

    public GeneticCowRenderer(EntityRenderDispatcher renderManagerIn, EntityRendererRegistry.Context context) {
        super(renderManagerIn, new CowEntityModel<>(), 0.3f);
        this.addFeature(new LayerDyeableFeatureRenderer(this, COW_LAYER_1, new CowHideColorLogic(3)));
        this.addFeature(new LayerDyeableFeatureRenderer(this, COW_LAYER_2, new CowSpotColorLogic(7)));
        this.bindTexture(COW_BASE);
    }
    @Override
    public void render(MobEntity entity, float x, float y, float z, float entityYaw, float partialTicks, float wtf) {
        super.render(entity, x, y, z, entityYaw, partialTicks, wtf);
    }
    protected Identifier getTexture(MobEntity entity) {
        return COW_BASE;
    }
}