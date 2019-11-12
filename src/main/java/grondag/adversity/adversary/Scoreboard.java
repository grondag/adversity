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

public class Scoreboard {
	final Factors scores = new Factors();
	final Factors weights = new Factors();

	{
		weights.resource = 0.25f;
		weights.control = 0.25f;
		weights.conquest = 0.25f;
		weights.prestige = 0.25f;
	}

	public static class Factors {
		/** material and energy - 1 means can do anything wanted in foreseeable future */
		public float resource = 0;

		/** defense and knowledge of environment, opponents */
		public float control = 0;

		/** elimination of opponents and destruction of their resources */
		public float conquest = 0;

		/** terrorizing, intimidating, surprising, taunting or otherwise making a big show */
		public float prestige = 0;

		public float product(Factors other) {
			return resource * other.resource + control * other.control + conquest * other.conquest + prestige * other.prestige;
		}
	}

	public float score() {
		return scores.product(weights);
	}
}
