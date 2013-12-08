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

package net.signalr.client.transports.websocket;

import java.security.InvalidParameterException;

import com.ning.http.client.websocket.WebSocket;
import com.ning.http.client.websocket.WebSocketTextListener;

public final class WebSocketTextListenerAdapter implements WebSocketTextListener {

	private final WebSocketTransport _transport;

	public WebSocketTextListenerAdapter(final WebSocketTransport transport) {
		if (transport == null)
			throw new InvalidParameterException("Transport must not be null");

		_transport = transport;
	}

	public void onOpen(final WebSocket webSocket) {
		_transport.onOpen();
	}

	public void onClose(final WebSocket webSocket) {
		_transport.onClose();
	}

	public void onFragment(final String fragment, final boolean last) {
	}

	public void onMessage(final String message) {
		_transport.onMessage(message);
	}

	public void onError(final Throwable throwable) {
		_transport.onError(throwable);
	}
}
