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

package grondag.adversity.adversary;

import grondag.fermion.simulator.SimulationTickable;
import grondag.fermion.simulator.Simulator;
import grondag.fermion.simulator.persistence.SimulationTopNode;
import grondag.fermion.varia.NBTDictionary;
import net.minecraft.nbt.CompoundTag;

public class Adversary extends SimulationTopNode implements SimulationTickable {
	public static final String ADVERSARY_TAG = NBTDictionary.claim("adversary_sim");

	public Adversary() {
		super(ADVERSARY_TAG);
	}

	@Override
	public void unload() {
		super.unload();
	}

	@Override
	public void afterCreated(Simulator sim) {
		super.afterCreated(sim);
	}

	@Override
	public void afterDeserialization() {
		super.afterDeserialization();
	}

	@Override
	public void loadNew() {
		super.loadNew();
	}

	@Override
	public void fromTag(CompoundTag tag) {
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		return tag;
	}

	@Override
	public boolean doesUpdateOnTick() {
		return true;
	}

	@Override
	public void doOnTick() {
		//		markDirty();
	}

	@Override
	public boolean doesUpdateOffTick() {
		return true;
	}

	@Override
	public void doOffTick() {
		//		markDirty();
	}
}
