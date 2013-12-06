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
import java.util.concurrent.Future;

import net.signalr.client.Connection;

public abstract class AbstractTransport implements Transport {

	private TransportListener _listener;

	public AbstractTransport() {
		_listener = new NullTransportListener();
	}

	public void onOpen() {
		_listener.onOpen(this);
	}

	public void onClose() {
		_listener.onClose(this);
	}

	public void onError(Throwable throwable) {
		_listener.onError(this, throwable);
	}

	public void onMessage(String message) {
		_listener.onMessage(message);
	}

	public void setTransportListener(TransportListener listener) {
		if (listener == null)
			throw new InvalidParameterException("Listener must not be null");

		_listener = listener;
	}

	public abstract Future<NegotiationResponse> negotiate(Connection connection, String connectionData);

	public abstract Future<?> start(Connection connection, String connectionData);

	public abstract Future<?> send(Connection connection, String connectionData, String data);

	public abstract void abort(Connection connection, long timeout, String connectionData);
}
