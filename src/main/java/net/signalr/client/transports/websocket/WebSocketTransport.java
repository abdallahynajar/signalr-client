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
import net.signalr.client.serialization.Serializer;
import net.signalr.client.transports.AbstractTransport;
import net.signalr.client.transports.NegotiationResponse;
import net.signalr.client.transports.TransportException;

public final class WebSocketTransport extends AbstractTransport {

	public static final String TRANSPORT = "webSockets";

	private final AsyncHttpClient _client;

	public WebSocketTransport() {
		_client = new AsyncHttpClient();
	}

	@Override
	public Future<NegotiationResponse> negotiate(final Connection connection, String connectionData) {
		String url = connection.getUrl();
		BoundRequestBuilder boundRequestBuilder = _client.prepareGet(url + "negotiate");

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

			return Futures.then(negotiate, new Function<Response, NegotiationResponse>() {
				@Override
				public NegotiationResponse invoke(Response response) {
					String data;
					
					try {
						data = response.getResponseBody();
					} catch (IOException e) {
						throw new TransportException(e);
					}
					Serializer serializer = connection.getSerializer();

					return serializer.deserialize(data, NegotiationResponse.class);
				}
			});

		} catch (IOException e) {
			throw new TransportException(e);
		}
	}

	@Override
	public Future<?> start(Connection connection, String connectionData) {
		String url = connection.getUrl();
		BoundRequestBuilder boundRequestBuilder = _client.prepareGet(url + "connect");

		// Add headers.
		Map<String, Collection<String>> headers = connection.getHeaders();

		boundRequestBuilder.setHeaders(headers);

		// Add query parameters.
		Map<String, Collection<String>> parameters = connection.getQueryParameters();

		boundRequestBuilder.setQueryParameters(new FluentStringsMap(parameters));
		String connectionToken = connection.getConnectionToken();

		boundRequestBuilder.addQueryParameter("connectionToken", connectionToken);
		boundRequestBuilder.addQueryParameter("connectionData", connectionData);
		boundRequestBuilder.addQueryParameter("tid", "7");
		boundRequestBuilder.addQueryParameter("transport", TRANSPORT);

		try {
			WebSocketUpgradeHandler.Builder builder = new WebSocketUpgradeHandler.Builder();

			builder.addWebSocketListener(new WebSocketTextListenerAdapter(this));
			
			return boundRequestBuilder.execute(builder.build());
		} catch (IOException e) {
			throw new TransportException(e);
		}
	}

	@Override
	public Future<?> send(Connection connection, String connectionData, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Connection connection, long timeout, String connectionData) {
		// TODO Auto-generated method stub
	}
}
