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
package grondag.adversity.registry;

import static grondag.adversity.Adversity.REG;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import grondag.adversity.block.tree.ForebodingShrubFeature;

public enum AdversityFeatures {
	;

	public static final Feature<DefaultFeatureConfig> FOREBODING_SHRUB = Registry.register(Registry.FEATURE, REG.id("sweet_berry_bush"), new ForebodingShrubFeature(DefaultFeatureConfig::deserialize));

	static {
		addShrub(Biomes.BADLANDS);
		addShrub(Biomes.BADLANDS_PLATEAU);
		addShrub(Biomes.ERODED_BADLANDS);
		addShrub(Biomes.MODIFIED_BADLANDS_PLATEAU);
		addShrub(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU);
		addShrub(Biomes.SAVANNA);
		addShrub(Biomes.SAVANNA_PLATEAU);
		addShrub(Biomes.SHATTERED_SAVANNA);
		addShrub(Biomes.SHATTERED_SAVANNA_PLATEAU);
		addShrub(Biomes.WOODED_BADLANDS_PLATEAU);
	}

	static void addShrub(Biome target) {
		target.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, FOREBODING_SHRUB.configure(new DefaultFeatureConfig()).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(1, 0.1F))));
	}
}
