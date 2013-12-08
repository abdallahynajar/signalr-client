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

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;

public final class FailedImmediateFuture<V> extends AbstractImmediateFuture<V> {

	private final Throwable _cause;

	public FailedImmediateFuture(final Throwable cause) {
		if (cause == null)
			throw new InvalidParameterException("Throwable must not be null");

		_cause = cause;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		throw new ExecutionException(_cause);
	}
}
