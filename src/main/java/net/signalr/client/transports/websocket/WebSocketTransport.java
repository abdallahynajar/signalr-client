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

	/**
	 * Initializes a new instance of the <code>WebSocketTransport</code> class.
	 */
	public WebSocketTransport() {
		_client = new AsyncHttpClient();
	}

	private Future<?> connect(Connection connection, String connectionData, boolean reconnect) {
		URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), reconnect ? "reconnect" : "connect");
		String schema = uriBuilder.getSchema().equals("https") ? "wss" : "ws";

		uriBuilder.setSchema(schema);
		BoundRequestBuilder boundRequestBuilder = _client.prepareGet(uriBuilder.toString());

		// Add headers.
		Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Add query parameters.
		Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		String connectionToken = connection.getConnectionToken();

		boundRequestBuilder.addQueryParameter("connectionToken", connectionToken);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);
		String transport = getName();

		boundRequestBuilder.addQueryParameter("transport", transport);
		WebSocketUpgradeHandler.Builder builder = new WebSocketUpgradeHandler.Builder();

		builder.addWebSocketListener(new WebSocketTextListenerAdapter(this));

		try {
			return boundRequestBuilder.execute(builder.build());
		} catch (IOException e) {
			return Futures.failed(e);
		}
	}

	@Override
	public String getName() {
		return "webSockets";
	}

	@Override
	public Future<String> negotiate(final Connection connection, String connectionData) {
		URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), "negotiate");
		BoundRequestBuilder boundRequestBuilder = _client.prepareGet(uriBuilder.toString());

		// Add headers.
		Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Add query parameters.
		Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		String protocol = connection.getProtocol();

		boundRequestBuilder.addQueryParameter("clientProtocol", protocol);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);

		try {
			Future<Response> negotiate = boundRequestBuilder.execute();

			return Futures.continueWith(negotiate, new Function<Response, String>() {
				@Override
				public String invoke(Response response) throws Exception {
					int statusCode = response.getStatusCode();

					if (statusCode != 200)
						throw new IllegalStateException("Negotiate failed: " + statusCode + " " + response.getStatusText());

					return response.getResponseBody();
				}
			});
		} catch (IOException e) {
			return Futures.failed(e);
		}
	}

	@Override
	public Future<?> start(Connection connection, String connectionData) {
		return connect(connection, connectionData, false);
	}

	@Override
	public Future<?> send(Connection connection, String connectionData, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<?> abort(Connection connection, String connectionData) {
		URIBuilder uriBuilder = new URIBuilder(connection.getUrl(), "abort");
		BoundRequestBuilder boundRequestBuilder = _client.preparePost(uriBuilder.toString());

		// Add headers.
		Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Add query parameters.
		Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		String connectionToken = connection.getConnectionToken();

		boundRequestBuilder.addQueryParameter("connectionToken", connectionToken);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);
		String transport = getName();

		boundRequestBuilder.addQueryParameter("transport", transport);

		try {
			Future<Response> negotiate = boundRequestBuilder.execute();

			return Futures.continueWith(negotiate, new Function<Response, String>() {
				@Override
				public String invoke(Response response) throws Exception {
					int statusCode = response.getStatusCode();

					if (statusCode != 200)
						throw new IllegalStateException("Abort failed: " + statusCode + " " + response.getStatusText());

					return response.getResponseBody();
				}
			});
		} catch (IOException e) {
			return Futures.failed(e);
		}
	}
}
