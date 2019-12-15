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
package grondag.adversity.compat.rei;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeDisplay;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;

public abstract class AlchemicalDisplay<T extends Recipe<?>> implements RecipeDisplay {

	protected List<List<EntryStack>> inputs;
	protected List<EntryStack> output;
	protected T display;

	public AlchemicalDisplay(T recipe) {
		this(recipe.getPreviewInputs(), recipe.getOutput());
		this.display = recipe;
	}

	public AlchemicalDisplay(DefaultedList<Ingredient> ingredients, ItemStack output) {
		final ImmutableList.Builder<List<EntryStack>> outer = ImmutableList.builder();

		for(final Ingredient i : ingredients) {
			final ImmutableList.Builder<EntryStack> inner = ImmutableList.builder();

			for(final ItemStack stack : i.getMatchingStacksClient()) {
				inner.add(EntryStack.create(stack));
			}

			outer.add(inner.build());
		}

		this.inputs = outer.build();
		this.output = Collections.singletonList(EntryStack.create(output));
	}

	@Override
	public Optional<Identifier> getRecipeLocation() {
		return Optional.ofNullable(display).map(Recipe::getId);
	}

	@Override
	public List<List<EntryStack>> getInputEntries() {
		return inputs;
	}

	@Override
	public List<EntryStack> getOutputEntries() {
		return this.output;
	}

	@Override
	public List<List<EntryStack>> getRequiredEntries() {
		return getInputEntries();
	}
}
