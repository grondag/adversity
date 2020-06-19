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

package grondag.adversity.gui;

import net.minecraft.client.util.math.MatrixStack;

import grondag.adversity.Adversity;
import grondag.fermion.gui.AbstractSimpleScreen;
import grondag.fonthack.FontHackClient;
import grondag.mcmd.MarkdownControl;
import grondag.mcmd.MarkdownLoader;

public class NoteScreen extends AbstractSimpleScreen {
	grondag.mcmd.MarkdownControl md;

	public NoteScreen() {
		super();
	}

	@Override
	public void init() {
		textRenderer = FontHackClient.getTextRenderer(FontHackClient.READING_FONT);
		super.init();
	}

	@Override
	public void addControls() {
		md = new MarkdownControl(this, MarkdownLoader.get(Adversity.REG.id("sb_scale")), FontHackClient.READING_FONT);
		md.setLeft(screenLeft() + theme.externalMargin);
		md.setTop(screenTop() + theme.externalMargin);
		md.setWidth(screenWidth() - theme.externalMargin * 2);
		md.setHeight(screenHeight() - theme.externalMargin * 2);

		children.add(md);
	}

	@Override
	protected void drawControls(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		md.drawControl(matrixStack, mouseX, mouseY, partialTicks);
	}
}
