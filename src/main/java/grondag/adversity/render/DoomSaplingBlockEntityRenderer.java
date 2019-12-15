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
package grondag.adversity.render;

import java.util.Random;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;

import grondag.adversity.block.tree.DoomSaplingBlockEntity;

public class DoomSaplingBlockEntityRenderer extends BlockEntityRenderer<DoomSaplingBlockEntity> {
	public DoomSaplingBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);
	}

	@Override
	public void render(DoomSaplingBlockEntity sapling, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int lightmap, int overlay) {
		final float[] xOffset = new float[8];
		final float[] zOffset = new float[8];
		float xNext = 0.0F;
		float zNext = 0.0F;
		final Random rand = sapling.renderRand;
		rand.setSeed(sapling.renderSeed);

		for(int i = 7; i >= 0; --i) {
			xOffset[i] = xNext;
			zOffset[i] = zNext;
			xNext += (rand.nextInt(11) - 5);
			zNext += (rand.nextInt(11) - 5);
		}

		final VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
		final Matrix4f matrix4f = matrixStack.peek().getModel();

		for(int i = 0; i < 4; ++i) {
			rand.setSeed(sapling.renderSeed);

			for(int j = 0; j < 3; ++j) {
				int yMax = 7;
				int yMin = 0;

				if (j > 0) {
					yMax = 7 - j;
				}

				if (j > 0) {
					yMin = yMax - 2;
				}

				float xRand0 = xOffset[yMax] - xNext;
				float zRand0 = zOffset[yMax] - zNext;

				for(int yStep = yMax; yStep >= yMin; --yStep) {
					final float xRand1 = xRand0;
					final float zRand1 = zRand0;

					if (j == 0) {
						xRand0 += (rand.nextInt(11) - 5);
						zRand0 += (rand.nextInt(11) - 5);
					} else {
						xRand0 += (rand.nextInt(31) - 15);
						zRand0 += (rand.nextInt(31) - 15);
					}

					float y = 0.1F + i * 0.2F;

					if (j == 0) {
						y = (float)(y * (yStep * 0.1D + 1.0D));
					}

					float z = 0.1F + i * 0.2F;
					if (j == 0) {
						z *= (yStep - 1) * 0.1F + 1.0F;
					}

					renderInner(matrix4f, vertexConsumer, xRand0, zRand0, yStep, xRand1, zRand1, 0.45F, 0.45F, 0.5F, y, z, false, false, true, false);
					renderInner(matrix4f, vertexConsumer, xRand0, zRand0, yStep, xRand1, zRand1, 0.45F, 0.45F, 0.5F, y, z, true, false, true, true);
					renderInner(matrix4f, vertexConsumer, xRand0, zRand0, yStep, xRand1, zRand1, 0.45F, 0.45F, 0.5F, y, z, true, true, false, true);
					renderInner(matrix4f, vertexConsumer, xRand0, zRand0, yStep, xRand1, zRand1, 0.45F, 0.45F, 0.5F, y, z, false, true, false, false);
				}
			}
		}
	}

	private static void renderInner(Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, int i, float h, float j, float k, float l, float m, float n, float o, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
		vertexConsumer.vertex(matrix4f, f + (bl ? o : -o), i * 16, g + (bl2 ? o : -o)).color(k, l, m, 0.3F).next();
		vertexConsumer.vertex(matrix4f, h + (bl ? n : -n), (i + 1) * 16, j + (bl2 ? n : -n)).color(k, l, m, 0.3F).next();
		vertexConsumer.vertex(matrix4f, h + (bl3 ? n : -n), (i + 1) * 16, j + (bl4 ? n : -n)).color(k, l, m, 0.3F).next();
		vertexConsumer.vertex(matrix4f, f + (bl3 ? o : -o), i * 16, g + (bl4 ? o : -o)).color(k, l, m, 0.3F).next();
	}

	@Override
	public boolean rendersOutsideBoundingBox(DoomSaplingBlockEntity blockEntity) {
		return true;
	}
}
