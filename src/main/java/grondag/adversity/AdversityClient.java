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

import grondag.adversity.block.player.WardedWoodSignBlockEntity;
import grondag.adversity.entity.WalkerEntity;
import grondag.adversity.entity.WalkerEntityRenderer;
import grondag.adversity.entity.WardingEffect;
import grondag.adversity.model.BasinModel;
import grondag.adversity.model.BrazierModel;
import grondag.adversity.model.ChannelModel;
import grondag.adversity.model.HeartModel;
import grondag.adversity.model.LogModel;
import grondag.adversity.model.TerminalModel;
import grondag.adversity.packet.AlchemyCraftS2C;
import grondag.adversity.packet.DoomS2C;
import grondag.adversity.packet.WalkerPulseS2C;
import grondag.adversity.packet.XpDrainS2C;
import grondag.adversity.particle.BasinParticle.BasinParticleFactory;
import grondag.adversity.particle.BrazierParticle.BrazierParticleFactory;
import grondag.adversity.particle.IdleParticle.IdleParticleFactory;
import grondag.adversity.particle.SummoningParticle.SummoningParticleFactory;
import grondag.adversity.particle.WakingParticle.WakingParticleFactory;
import grondag.adversity.particle.WalkerPulseParticle.WalkerExplosionParticleFactory;
import grondag.adversity.particle.WalkerPulseParticle.WalkerPulseParticleFactory;
import grondag.adversity.particle.WardedFlameParticle;
import grondag.adversity.registry.AdversityFluids;
import grondag.adversity.registry.AdversityItems;
import grondag.adversity.registry.AdversityParticles;
import grondag.adversity.render.DoomEffectRender;
import grondag.fermion.block.sign.OpenSignRenderer;
import grondag.fermion.client.ClientRegistrar;
import grondag.fermion.client.models.SimpleUnbakedModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.SignBlockEntityModel;
import net.minecraft.client.texture.SpriteAtlasTexture;

public class AdversityClient implements ClientModInitializer {
	public static final ClientRegistrar REGISTRAR = new ClientRegistrar(Adversity.MOD_ID);

	@Override
	public void onInitializeClient() {
		SimpleUnbakedModel model = new SimpleUnbakedModel(LogModel::create, LogModel.TEXTURES);
		REGISTRAR.modelVariant("doom_log", model);
		REGISTRAR.modelVariant("doom_log_p", model);

		model = new SimpleUnbakedModel(ChannelModel::create, ChannelModel.CHANNEL_TEXTURES);
		REGISTRAR.modelVariant("doom_log_channel", model);
		REGISTRAR.modelVariant("doom_log_channel_p", model);

		model = new SimpleUnbakedModel(TerminalModel::create, TerminalModel.TERMINAL_TEXTURES);
		REGISTRAR.modelVariant("doom_log_terminal", model);
		REGISTRAR.modelVariant("doom_log_terminal_p", model);

		model = new SimpleUnbakedModel(BasinModel::create, BasinModel.TEXTURES);
		REGISTRAR.modelVariant("inert_alchemical_basin", model);
		REGISTRAR.modelVariant("alchemical_basin", model);
		REGISTRAR.modelVariant("alchemical_basin_frame", new SimpleUnbakedModel(BasinModel::createFrame, BasinModel.TEXTURES));

		model = new SimpleUnbakedModel(BrazierModel::create, BrazierModel.TEXTURES);
		REGISTRAR.modelVariant("inert_alchemical_brazier", model);
		REGISTRAR.modelVariant("alchemical_brazier", model);
		REGISTRAR.modelVariant("alchemical_brazier_frame", new SimpleUnbakedModel(BrazierModel::createFrame, BrazierModel.TEXTURES));

		REGISTRAR.modelVariant("doom_heart", new SimpleUnbakedModel(HeartModel::create, HeartModel.TERMINAL_TEXTURES));

		REGISTRAR.simpleRandomModel("doom_leaves", "block/doom_leaves_0_0", "block/doom_leaves_0_1", "block/doom_leaves_0_2", "block/doom_leaves_0_3");
		REGISTRAR.simpleRandomModel("doomed_residue_block", "block/doomed_residue_block");
		REGISTRAR.simpleRandomModel("warding_essence_block", "block/warding_essence_block");

		ClientSidePacketRegistry.INSTANCE.register(AlchemyCraftS2C.IDENTIFIER, AlchemyCraftS2C::handle);
		ClientSidePacketRegistry.INSTANCE.register(DoomS2C.IDENTIFIER, DoomS2C::handle);
		ClientSidePacketRegistry.INSTANCE.register(XpDrainS2C.IDENTIFIER, XpDrainS2C::handle);
		ClientSidePacketRegistry.INSTANCE.register(WalkerPulseS2C.IDENTIFIER, WalkerPulseS2C::handle);

		REGISTRAR.fluidRenderHandler(AdversityFluids.ICHOR, AdversityFluids.FLOWING_ICHOR, AdversityFluids.ICHOR_COLOR, "block/water_still", "block/water_flow");

		ParticleFactoryRegistry.getInstance().register(AdversityParticles.ALCHEMY_IDLE, IdleParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.ALCHEMY_WAKING, WakingParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.BASIN_ACTIVE, BasinParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.BRAZIER_ACTIVE, BrazierParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.WARDED_FLAME, WardedFlameParticle.WardedFlameParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.SUMMONING, SummoningParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.WALKER_PULSE, WalkerPulseParticleFactory::new);
		ParticleFactoryRegistry.getInstance().register(AdversityParticles.WALKER_EXPLOSION, WalkerExplosionParticleFactory::new);

		ClientSpriteRegistryCallback.event(SpriteAtlasTexture.PARTICLE_ATLAS_TEX).register((atlasTexture, registry) -> {
			registry.register(REGISTRAR.id("warded_flame"));
			for (int i = 0; i < 8; i++) {
				registry.register(REGISTRAR.id("pulse_" + i));
			}
		});

		BlockEntityRendererRegistry.INSTANCE.register(WardedWoodSignBlockEntity.class, new OpenSignRenderer(REGISTRAR.id("textures/entity/warded_wood_sign.png"), new SignBlockEntityModel()));
		//BlockEntityRendererRegistry.INSTANCE.register(DoomSaplingBlockEntity.class, new DoomSaplingBlockEntityRenderer());

		DoomEffectRender.init(REGISTRAR);

		ColorProviderRegistry.ITEM.register((s, i) -> WardingEffect.COLOR, AdversityItems.WARDING_POTION);
		ColorProviderRegistry.ITEM.register((s, i) -> 0xFFFFFFFF, AdversityItems.MILK_POTION);
		ColorProviderRegistry.ITEM.register((s, i) -> 0xFFB6D8FF, AdversityItems.SALVATION_POTION);

		EntityRendererRegistry.INSTANCE.register(WalkerEntity.class, (dispatcher, context) -> new WalkerEntityRenderer(dispatcher));

		Adversity.PLAYER_PROXY = () -> MinecraftClient.getInstance().player;
	}
}
