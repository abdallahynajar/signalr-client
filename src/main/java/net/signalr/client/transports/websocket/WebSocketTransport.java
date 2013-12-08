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

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.ning.http.client.websocket.WebSocket;
import com.ning.http.client.websocket.WebSocketUpgradeHandler;

import net.signalr.client.Connection;
import net.signalr.client.concurrent.Function;
import net.signalr.client.concurrent.Futures;
import net.signalr.client.transports.AbstractTransport;
import net.signalr.client.util.URIBuilder;

/**
 * Represents the WebSocket transport.
 * 
 * @author mtamme
 */
public final class WebSocketTransport extends AbstractTransport {

	private final AsyncHttpClient _client;

	private WebSocket _webSocket;

	/**
	 * Initializes a new instance of the <code>WebSocketTransport</code> class.
	 */
	public WebSocketTransport() {
		_client = new AsyncHttpClient();

		_webSocket = null;
	}

	private Future<?> connect(final Connection connection, final String connectionData, final boolean reconnect) {
		final URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), reconnect ? "reconnect" : "connect");
		final String schema = uriBuilder.getSchema().equals("https") ? "wss" : "ws";

		uriBuilder.setSchema(schema);
		final BoundRequestBuilder boundRequestBuilder = _client.prepareGet(uriBuilder.toString());

		// Set query parameters.
		final Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		final String connectionToken = connection.getConnectionToken();

		boundRequestBuilder.addQueryParameter("connectionToken", connectionToken);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);
		final String transport = getName();

		boundRequestBuilder.addQueryParameter("transport", transport);

		// Set headers.
		final Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Setup WebSocket upgrade handler.
		final WebSocketUpgradeHandler.Builder builder = new WebSocketUpgradeHandler.Builder();

		builder.addWebSocketListener(new WebSocketTextListenerAdapter(this));

		try {
			final Future<WebSocket> webSocketFuture = boundRequestBuilder.execute(builder.build());

			return Futures.continueWith(webSocketFuture, new Function<WebSocket, Void>() {
				@Override
				public Void invoke(final WebSocket webSocket) throws Exception {
					setWebSocket(webSocket);
					return null;
				}
			});
		} catch (final IOException e) {
			return Futures.failed(e);
		}
	}

	private void setWebSocket(WebSocket webSocket) {
		if ((_webSocket != null) && _webSocket.isOpen())
			_webSocket.close();

		_webSocket = webSocket;
	}

	@Override
	public String getName() {
		return "webSockets";
	}

	@Override
	public Future<String> negotiate(final Connection connection, final String connectionData) {
		final URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), "negotiate");
		final BoundRequestBuilder boundRequestBuilder = _client.prepareGet(uriBuilder.toString());

		// Set query parameters.
		final Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		final String protocol = connection.getProtocol();

		boundRequestBuilder.addQueryParameter("clientProtocol", protocol);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);

		// Set headers.
		final Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		try {
			final Future<Response> responseFuture = boundRequestBuilder.execute();

			return Futures.continueWith(responseFuture, new Function<Response, String>() {
				@Override
				public String invoke(final Response response) throws Exception {
					final int statusCode = response.getStatusCode();

					if (statusCode != 200)
						throw new IllegalStateException("Negotiate failed: " + statusCode + " " + response.getStatusText());

					return response.getResponseBody();
				}
			});
		} catch (final IOException e) {
			return Futures.failed(e);
		}
	}

	@Override
	public Future<?> start(final Connection connection, final String connectionData) {
		return connect(connection, connectionData, false);
	}

	@Override
	public Future<?> send(final Connection connection, final String connectionData, final String data) {
		final WebSocket webSocket = _webSocket;

		if ((webSocket == null) || !webSocket.isOpen())
			return Futures.failed(new IllegalStateException("Not connected"));

		return Futures.immediate(webSocket.sendTextMessage(data));
	}

	@Override
	public Future<?> abort(final Connection connection, final String connectionData) {
		final WebSocket webSocket = _webSocket;

		if ((webSocket == null) || !webSocket.isOpen())
			return Futures.failed(new IllegalStateException("Not connected"));

		webSocket.close();

		final URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), "abort");
		final BoundRequestBuilder boundRequestBuilder = _client.preparePost(uriBuilder.toString());

		// Set query parameters.
		final Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		final String connectionToken = connection.getConnectionToken();

		boundRequestBuilder.addQueryParameter("connectionToken", connectionToken);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);
		final String transport = getName();

		boundRequestBuilder.addQueryParameter("transport", transport);

		// Set headers.
		final Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Set body.
		boundRequestBuilder.setBody(new byte[0]);

		try {
			final Future<Response> responseFuture = boundRequestBuilder.execute();

			return Futures.continueWith(responseFuture, new Function<Response, String>() {
				@Override
				public String invoke(final Response response) throws Exception {
					final int statusCode = response.getStatusCode();

					if (statusCode != 200)
						throw new IllegalStateException("Abort failed: " + statusCode + " " + response.getStatusText());

					return response.getResponseBody();
				}
			});
		} catch (final IOException e) {
			return Futures.failed(e);
		}
	}
}
