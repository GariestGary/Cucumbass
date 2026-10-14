package io.github.gariestgary.cucumbass.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.gariestgary.cucumbass.Cucumbass;
import io.github.gariestgary.cucumbass.data.CucumberAttachmentTypes;
import io.github.gariestgary.cucumbass.data.InstalledCucumber;
import java.util.List;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/** Renders only the synchronized attachment; gameplay state remains server-authoritative. */
public final class PlayerCucumberRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Cucumbass.MOD_ID, "installed_cucumber"), "main");
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/misc/white.png");
    private final ModelPart cucumber;

    public PlayerCucumberRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, ModelPart cucumber) {
        super(renderer);
        this.cucumber = cucumber;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("cucumber", CubeListBuilder.create().texOffs(0, 0).addBox(-CucumberRenderGeometry.THICKNESS / 2.0F, -CucumberRenderGeometry.THICKNESS / 2.0F, -CucumberRenderGeometry.BODY_PENETRATION, CucumberRenderGeometry.THICKNESS, CucumberRenderGeometry.THICKNESS, CucumberRenderGeometry.LENGTH + CucumberRenderGeometry.BODY_PENETRATION), PartPose.ZERO);
        return LayerDefinition.create(mesh, 16, 16);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        List<InstalledCucumber> installed = player.getData(CucumberAttachmentTypes.PLAYER_CUCUMBERS.get()).slots().entries();
        if (installed.isEmpty()) return;
        poseStack.pushPose();
        // Follows body pose/orientation (including crouch and swim), but deliberately not head rotation.
        getParentModel().body.translateAndRotate(poseStack);
        poseStack.translate(CucumberRenderGeometry.ATTACH_X / 16.0F, CucumberRenderGeometry.ATTACH_Y / 16.0F, CucumberRenderGeometry.ATTACH_Z / 16.0F);
        for (int index = 0; index < installed.size(); index++) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(CucumberRenderGeometry.sideAngle(index)));
            poseStack.mulPose(Axis.XP.rotationDegrees(CucumberRenderGeometry.upAngle(index)));
            cucumber.render(poseStack, buffer.getBuffer(RenderType.entitySolid(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 0xFF3FAF3F);
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
