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

package net.signalr.client.transports;

import java.security.InvalidParameterException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents an abstract transport.
 * 
 * @author mtamme
 */
public abstract class AbstractTransport implements Transport {

	private final CopyOnWriteArrayList<TransportListener> _listeners;

	public AbstractTransport() {
		_listeners = new CopyOnWriteArrayList<TransportListener>();
	}

	public void addTransportListener(final TransportListener listener) {
		if (listener == null)
			throw new InvalidParameterException("Listener must not be null");

		_listeners.add(listener);
	}

	public void removeTransportListener(final TransportListener listener) {
		if (listener == null)
			throw new InvalidParameterException("Listener must not be null");

		_listeners.remove(listener);
	}

	public void onOpen() {
		for (final TransportListener listener : _listeners) {
			listener.onOpen(this);
		}
	}

	public void onClose() {
		for (final TransportListener listener : _listeners) {
			listener.onClose(this);
		}
	}

	public void onMessage(final String message) {
		for (final TransportListener listener : _listeners) {
			listener.onMessage(this, message);
		}
	}

	public void onError(final Throwable throwable) {
		for (final TransportListener listener : _listeners) {
			listener.onError(this, throwable);
		}
	}
}
