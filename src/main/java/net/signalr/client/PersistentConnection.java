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

import net.signalr.client.serializers.Serializer;
import net.signalr.client.transports.NegotiationResponse;
import net.signalr.client.transports.Transport;

public class PersistentConnection implements Connection {

	private static final String PROTOCOL = "1.3";

	private static final long DEFAULT_ABORT_TIMEOUT = 30 * 1000;

	private final String _url;

	private final Transport _transport;

	private final Map<String, Collection<String>> _headers;

	private final Map<String, Collection<String>> _queryParameters;

	private final Serializer _serializer;

	private String _protocol;

	private String _connectionToken;

	public PersistentConnection(String url, Transport transport, Serializer serializer) {
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
		_connectionToken = "";
	}

	public String getUrl() {
		return _url;
	}

	public Map<String, Collection<String>> getHeaders() {
		return _headers;
	}

	public void addHeader(String name, String value) {
		Collection<String> values = _headers.get(name);

		if (values == null)
			values = new ArrayList<String>();

		values.add(value);
	}

	public Map<String, Collection<String>> getQueryParameters() {
		return _queryParameters;
	}

	public void addQueryParameter(String name, String value) {
		Collection<String> values = _queryParameters.get(name);

		if (values == null)
			values = new ArrayList<String>();

		values.add(value);
	}

	public String getProtocol() {
		return _protocol;
	}

	public void setProtocol(String protocol) {
		if (protocol == null)
			throw new InvalidParameterException("Protocol must not be null");

		_protocol = protocol;
	}

	public String getConnectionToken() {
		return _connectionToken;
	}

	public void setConnectionToken(String connectionToken) {
		if (connectionToken == null)
			throw new InvalidParameterException("Connection token must not be null");

		_connectionToken = connectionToken;
	}

	public Serializer getSerializer() {
		return _serializer;
	}

	public Future<?> start(ConnectionListener listener) {
		String connectionData = null;
		Future<NegotiationResponse> negotiate = _transport.negotiate(this, connectionData);

		return _transport.start(this, connectionData);
	}

	public void stop() {
		String connectionData = null;

		_transport.abort(this, DEFAULT_ABORT_TIMEOUT, connectionData);
	}

	public Future<?> send(String data) {
		String connectionData = null;

		return _transport.send(this, connectionData, data);
	}
}
