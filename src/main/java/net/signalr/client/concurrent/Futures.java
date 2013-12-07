/*
 * Copyright © Martin Tamme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.signalr.client.concurrent;

import java.util.concurrent.Future;

public final class Futures {

	public static Future<?> empty() {
		return immediate(null);
	}

	public static <V> Future<V> immediate(V value) {
		return new ImmediateFuture<V>(value);
	}

	public static <I, O> Future<O> continueWith(final Future<I> future, final Function<? super I, ? extends O> function) {
		return new ContinuationFuture<I, O>(future, function);
	}

	public static <I, O> Future<O> continueWith(final Future<I> future, final AsyncFunction<? super I, ? extends O> function) {
		// TODO Auto-generated method stub
		return null;
	}
}
