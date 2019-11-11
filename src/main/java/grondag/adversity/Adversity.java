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
package grondag.adversity;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import grondag.adversity.block.treeheart.DoomTreeTracker;
import grondag.adversity.registry.AdversityBlocks;
import grondag.adversity.registry.AdversityEffects;
import grondag.adversity.registry.AdversityEntities;
import grondag.adversity.registry.AdversityFeatures;
import grondag.adversity.registry.AdversityFluids;
import grondag.adversity.registry.AdversityItems;
import grondag.adversity.registry.AdversityLoot;
import grondag.adversity.registry.AdversityParticles;
import grondag.adversity.registry.AdversityRecipes;
import grondag.adversity.registry.AdversitySounds;
import grondag.adversity.registry.AdversityTags;
import grondag.fermion.registrar.Registrar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.event.server.ServerStopCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceType;

/**
 * FEAT: mobs - floaters
 * FEAT: alchemical mob drops / recipes -> alternate acquisition path for engines
 * FEAT: tree attacks
 * FEAT: damage / power / gear balance
 * FEAT: doom vines (can drop fuel without enchanting)
 * FEAT: achievements
 * FEAT: improve tree harvest light show
 * FEAT: improve tree harvest sounds
 * FEAT: configs
 * FEAT: doom heart -> fabricator recipe
 * FEAT: fabricator recipes
 * FEAT: end-game tools / gear using fabricator
 */

public class Adversity implements ModInitializer {
	public static final String MOD_ID = "adversity";
	public static final Logger LOGGER = LogManager.getLogger("Adversity");
	public static final Registrar REG = new Registrar(MOD_ID, "doom_sapling");

	static Supplier<PlayerEntity> PLAYER_PROXY = () -> null;

	/**
	 * Will be player on client side, null on server
	 */
	public static PlayerEntity player() {
		return PLAYER_PROXY.get();
	}

	@Override
	public void onInitialize() {
		AdversityBlocks.values();
		AdversityFluids.values();
		AdversityItems.values();
		AdversityRecipes.values();
		AdversitySounds.values();
		AdversityTags.values();
		AdversityParticles.values();
		AdversityFeatures.values();
		AdversityEffects.values();
		AdversityEntities.values();

		ServerStopCallback.EVENT.register(s -> DoomTreeTracker.clear());
		ServerStartCallback.EVENT.register(AdversityRecipes.HELPER::init);
		ServerStopCallback.EVENT.register(AdversityRecipes.HELPER::stop);
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(AdversityRecipes.HELPER);
		LootTableLoadingCallback.EVENT.register(AdversityLoot::init);
	}
}
