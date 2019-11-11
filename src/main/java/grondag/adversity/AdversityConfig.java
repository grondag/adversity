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

import java.io.File;
import java.io.FileOutputStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import blue.endless.jankson.Comment;
import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.MathHelper;

public enum AdversityConfig {
	;

	public static final boolean ADVERSARY_ACTIVE;
	public static final int MAX_ACTIVE_TREES;

	private static class Config {
		@Comment("Does the mod try to kill you?")
		boolean active_adversary = true;

		@Comment("How many doom trees can be actively growing at one time? 1-3")
		int max_active_trees = 1;

		void validate() {
			max_active_trees = MathHelper.clamp(max_active_trees, 1, 3);
		}
	}

	static {
		final Jankson jank = Jankson.builder().build();
		final Gson gson = new GsonBuilder().create();
		final File configFile = new File(FabricLoader.getInstance().getConfigDirectory(), "adversity.json5");
		Config config = new Config();

		if(configFile.exists()) {
			try {
				final JsonObject configJson = jank.load(configFile);
				final String regularized = configJson.toJson(false, false, 0);
				config = gson.fromJson(regularized, Config.class);
			} catch (final Exception e) {
				e.printStackTrace();
				Adversity.LOG.error("[Adversity] Unable to load config. Using default values.");
			}
		}

		config.validate();

		ADVERSARY_ACTIVE = config.active_adversary;
		MAX_ACTIVE_TREES = config.max_active_trees;

		try {
			final String result = jank.toJson(config).toJson(true, true, 0);
			if (!configFile.exists())
				configFile.createNewFile();

			try(
				FileOutputStream out = new FileOutputStream(configFile, false);
				) {
				out.write(result.getBytes());
				out.flush();
				out.close();
			}
		} catch (final Exception e) {
			e.printStackTrace();
			Adversity.LOG.error("[Adversity] Unable to save config.");
		}
	}
}
