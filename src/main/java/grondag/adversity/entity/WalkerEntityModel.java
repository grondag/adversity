/*******************************************************************************
 * Copyright (C) 2019 grondag
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package grondag.adversity.entity;

import java.util.Random;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import grondag.adversity.AdversityClient;

@Environment(EnvType.CLIENT)
public class WalkerEntityModel extends BipedEntityModel<WalkerEntity> {

	private final static float Y_OFFSET = -10f;
	private final Random random = new Random();
	private int pulseCount;

	private void setPivots() {
		torso.pivotY = Y_OFFSET;
		torso.pivotZ = -0.0F;
		rightArm.pivotZ = 0.0F;
		rightArm.pivotY = Y_OFFSET + 4.0F;

		leftArm.pivotZ = 0.0F;
		leftArm.pivotY = Y_OFFSET + 4.0F;

		rightLeg.pivotZ = 0.0F;
		rightLeg.pivotY = Y_OFFSET + 14.0F;

		leftLeg.pivotZ = 0.0F;
		leftLeg.pivotY = Y_OFFSET + 14.0F;

		head.pivotZ = -0.0F;
		head.pivotY = Y_OFFSET + 1;

		helmet.pivotX = head.pivotX;
		helmet.pivotY = head.pivotY;
		helmet.pivotZ = head.pivotZ;
	}

	private void init() {
		head = new ModelPart(this, 0, 0);
		head.addCuboid(-4F, -7.0F, -8.0F, 8, 8, 16, -1.50f);
		head.setPivot(0.0F, Y_OFFSET + 1, 0.0F);

		helmet = new ModelPart(this, 0, 0);
		helmet.addCuboid(-4F, -7.0F, -8.0F, 8, 8, 16, -1.50f);
		helmet.visible = false;

		torso = new ModelPart(this, 36, 32);
		torso.addCuboid(-1F, -1.5F, -1.0F, 2, 18, 2, 0f);
		torso.addCuboid(-6.0F, 4F, -1.0F, 12, 2, 2, 0f);
		torso.setTextureOffset(32, 0);
		torso.addCuboid(-3.0F, 3F, -2.0F, 6, 8, 4, 0f);
		torso.setPivot(0.0F, Y_OFFSET, 0.0F);

		rightArm = new ModelPart(this, 0, 0);
		rightArm.addCuboid(-2.5F, -1.0F, -1.5F, 3, 13, 3, 0f);

		rightArm.setTextureOffset(48, 59);
		rightArm.addCuboid(-3.5F, 11.0F, -0.5F, 1, 4, 1, 0.0f);
		rightArm.setTextureOffset(51, 59);
		rightArm.addCuboid(-1.5F, 11.0F, -2.5F, 1, 4, 1, 0.0f);
		rightArm.setTextureOffset(54, 59);
		rightArm.addCuboid(-1.5F, 11.0F,  1.5F, 1, 4, 1, 0.0f);
		rightArm.setPivot(-5.0F, Y_OFFSET + 2.0F, 0.0F);

		leftArm = new ModelPart(this, 48, 12);
		leftArm.mirror = true;
		leftArm.addCuboid(-0.5F, -1.0F, -1.5F, 3, 13, 3, 0f);

		leftArm.setTextureOffset(48, 59);
		leftArm.addCuboid( 2.5F, 11.0F, -0.5F, 1, 4, 1, 0.0f);
		leftArm.setTextureOffset(51, 59);
		leftArm.addCuboid( 0.5F, 11.0F, -2.5F, 1, 4, 1, 0.0f);
		leftArm.setTextureOffset(54, 59);
		leftArm.addCuboid( 0.5F, 11.0F,  1.5F, 1, 4, 1, 0.0f);
		leftArm.setPivot(5.0F, Y_OFFSET + 2.0F, 0.0F);

		rightLeg = new ModelPart(this, 0, 24);
		rightLeg.addCuboid(-3.0F, 0.0F, -1.5F, 3, 3, 6, 0F);

		rightLeg.setTextureOffset(0, 36);
		rightLeg.addCuboid(-3.0F, 3.0F, 1.5F, 3, 8, 3, 0F);

		rightLeg.setTextureOffset(0, 52);
		rightLeg.addCuboid(-3.0F, 11.0F, -1.5F, 3, 3, 6, 0F);

		rightLeg.setTextureOffset(36, 30);
		rightLeg.addCuboid(-3.0F, 14.0F, -1.5F, 3, 6, 3, 0F);

		rightLeg.setPivot(-1.0F, Y_OFFSET + 9.0F, 0.0F);

		leftLeg.mirror = false;
		leftLeg = new ModelPart(this, 18, 24);
		leftLeg.addCuboid(-1.0F, 0.0F, -1.5F, 3, 3, 6, 0F);

		leftLeg.setTextureOffset(18, 36);
		leftLeg.addCuboid(-1.0F, 3.0F, 1.5F, 3, 8, 3, 0F);

		leftLeg.setTextureOffset(18, 52);
		leftLeg.addCuboid(-1.0F, 11.0F, -1.5F, 3, 3, 6, 0F);

		leftLeg.setTextureOffset(48, 30);
		leftLeg.addCuboid(-1.0F, 14.0F, -1.5F, 3, 6, 3, 0F);

		leftLeg.setPivot(2.0F, Y_OFFSET + 9.0F, 0.0F);

		setPivots();
	}

	public WalkerEntityModel() {
		super(0.0F, Y_OFFSET, 64, 64);

		init();

		// Used for interactive debug of model changes
		// InvalidateRenderStateCallback.EVENT.register(this::init);
	}



	@Override
	public void copyStateTo(EntityModel<WalkerEntity> entityModel) {
		super.copyStateTo(entityModel);
		((WalkerEntityModel) entityModel).pulseCount = pulseCount;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int lightmap, int overlay, float r, float g, float b, float a) {

		final int pulseCount = this.pulseCount;

		if (pulseCount > 0) {
			random.setSeed(pulseCount);
			final float f = 0.002f * pulseCount;
			final float x = (float) (random.nextGaussian() * f);
			final float y = (float) (random.nextGaussian() * f);
			final float z = (float) (random.nextGaussian() * f);
			matrixStack.push();
			matrixStack.translate(x, y, z);
			head.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
			//helmet.render(tickDelta);
			matrixStack.pop();
		} else {
			head.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
			helmet.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
		}

		torso.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
		rightArm.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
		leftArm.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
		rightLeg.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);
		leftLeg.render(matrixStack, vertexConsumer, lightmap, overlay, r, g, b, a);

	}


	@Override
	public void setAngles(WalkerEntity walker, float f, float g, float h, float i, float j) {
		super.setAngles(walker, f, g, h, i, j);

		pulseCount = walker.pulseCount;
		head.visible = true;
		helmet.visible = false;

		//		torso.pitch = 0.0F;

		//		rightArm.pitch = rightArm.pitch * 0.5F;
		//		leftArm.pitch = leftArm.pitch * 0.5F;
		//		rightLeg.pitch = rightLeg.pitch * 0.5F;
		//		leftLeg.pitch = leftLeg.pitch * 0.5F;
		//
		//		if (rightArm.pitch > 0.4F) {
		//			rightArm.pitch = 0.4F;
		//		}
		//
		//		if (leftArm.pitch > 0.4F) {
		//			leftArm.pitch = 0.4F;
		//		}
		//
		//		if (rightArm.pitch < -0.4F) {
		//			rightArm.pitch = -0.4F;
		//		}
		//
		//		if (leftArm.pitch < -0.4F) {
		//			leftArm.pitch = -0.4F;
		//		}
		//
		//		if (rightLeg.pitch > 0.4F) {
		//			rightLeg.pitch = 0.4F;
		//		}
		//
		//		if (leftLeg.pitch > 0.4F) {
		//			leftLeg.pitch = 0.4F;
		//		}
		//
		//		if (rightLeg.pitch < -0.4F) {
		//			rightLeg.pitch = -0.4F;
		//		}
		//
		//		if (leftLeg.pitch < -0.4F) {
		//			leftLeg.pitch = -0.4F;
		//		}

		// helmet.pitch = head.pitch;
		// helmet.yaw = head.yaw;
		// helmet.roll = head.roll;

		setPivots();
	}

	public static class WalkerFeatureRenderer extends FeatureRenderer<WalkerEntity, WalkerEntityModel> {
		private static final Identifier SKIN = AdversityClient.REGISTRAR.id("textures/entity/walker_cracks.png");

		public WalkerFeatureRenderer(FeatureRendererContext<WalkerEntity, WalkerEntityModel> featureRendererContext) {
			super(featureRendererContext);
		}

		@Override
		public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, WalkerEntity walkerEntity, float f, float g, float h, float j, float k, float l) {
			final VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(SKIN));
			getContextModel().render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(walkerEntity, 0.0F), f, g, h, 1.0F);
		}
	}
}