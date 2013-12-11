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
import java.util.concurrent.Future;

import com.ning.http.client.websocket.WebSocket;

import net.signalr.client.concurrent.Function;
import net.signalr.client.concurrent.Futures;
import net.signalr.client.transports.Session;

final class WebSocketSession implements Session {

    private final WebSocket _webSocket;

    public WebSocketSession(final WebSocket webSocket) {
        if (webSocket == null)
            throw new InvalidParameterException("WebSocket must not be null");

        _webSocket = webSocket;
    }

    @Override
    public Future<Void> send(final String message) {
        return Futures.create(new Function<String, Void>() {
            @Override
            public Void invoke(String message) throws Exception {
                _webSocket.sendTextMessage(message);
                return null;
            }
        }, null);
    }

    @Override
    public void close() {
        _webSocket.close();
    }
}
