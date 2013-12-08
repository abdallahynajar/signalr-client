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

package net.signalr.client;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.signalr.client.concurrent.Function;
import net.signalr.client.concurrent.Futures;
import net.signalr.client.serialization.Serializer;
import net.signalr.client.transports.Transport;

public class PersistentConnection implements Connection {

	private static final String PROTOCOL = "1.3";

	private static final long DEFAULT_ABORT_TIMEOUT = 30;

	private final String _url;

	private final Transport _transport;

	private final Map<String, Collection<String>> _headers;

	private final Map<String, Collection<String>> _queryParameters;

	private final Serializer _serializer;

	private String _protocol;

	private String _connectionId;

	private String _connectionToken;

	private String _connectionData;

	private double _disconnectTimeout;

	public PersistentConnection(final String url, final Transport transport, final Serializer serializer) {
		if (url == null)
			throw new InvalidParameterException("URL must not be null");

		if (transport == null)
			throw new InvalidParameterException("Transport must not be null");

		if (serializer == null)
			throw new InvalidParameterException("Serializer must not be null");

		_url = url;
		_transport = transport;
		_serializer = serializer;

		_headers = new HashMap<String, Collection<String>>();
		_queryParameters = new HashMap<String, Collection<String>>();

		_protocol = PROTOCOL;
		_connectionId = null;
		_connectionToken = null;
		_connectionData = null;
		_disconnectTimeout = 0.0;
	}

	public String getUrl() {
		return _url;
	}

	public Map<String, Collection<String>> getHeaders() {
		return _headers;
	}

	public void addHeader(final String name, final String value) {
		Collection<String> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<String>();
			_headers.put(name, values);
		}

		values.add(value);
	}

	public Map<String, Collection<String>> getQueryParameters() {
		return _queryParameters;
	}

	public void addQueryParameter(final String name, final String value) {
		Collection<String> values = _queryParameters.get(name);

		if (values == null) {
			values = new ArrayList<String>();
			_queryParameters.put(name, values);
		}

		values.add(value);
	}

	public String getProtocol() {
		return _protocol;
	}

	public void setProtocol(final String protocol) {
		if (protocol == null)
			throw new InvalidParameterException("Protocol must not be null");

		_protocol = protocol;
	}

	public String getConnectionToken() {
		return _connectionToken;
	}

	public void setConnectionToken(final String connectionToken) {
		if (connectionToken == null)
			throw new InvalidParameterException("Connection token must not be null");

		_connectionToken = connectionToken;
	}

	public String getConnectionData() {
		return _connectionData;
	}

	public void setConnectionData(final String connectionData) {
		if (connectionData == null)
			throw new InvalidParameterException("Connection data must not be null");

		_connectionData = connectionData;
	}

	public Serializer getSerializer() {
		return _serializer;
	}

	public Future<?> start(final ConnectionListener listener) {
		final Future<String> negotiateFuture = _transport.negotiate(this, _connectionData);

		return Futures.continueWith(negotiateFuture, new Function<String, Object>() {
			@Override
			public Object invoke(final String data) throws Exception {
				final Serializer serializer = PersistentConnection.this.getSerializer();
				final NegotiationResponse negotiationResponse = serializer.deserialize(data, NegotiationResponse.class);
				
				if (!negotiationResponse.getProtocolVersion().equals(getProtocol()))
					throw new IllegalStateException("Invalid protocol version");

				_connectionId = negotiationResponse.getConnectionId();
				_connectionToken = negotiationResponse.getConnectionToken();
				_disconnectTimeout = negotiationResponse.getDisconnectTimeout();
				Future<?> startFuture = _transport.start(PersistentConnection.this, _connectionData);

				return startFuture.get();
			}
		});
	}

	public void stop() {
		final Future<?> abortFuture = _transport.abort(this, _connectionData);

		try {
			abortFuture.get(DEFAULT_ABORT_TIMEOUT, TimeUnit.SECONDS);
		} catch (final TimeoutException e) {
			abortFuture.cancel(true);
		} catch (final Exception e) {
			throw new ConnectionException(e);
		}
	}

	public Future<?> send(final String data) {
		return _transport.send(this, _connectionData, data);
	}
}
