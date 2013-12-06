package net.signalr.client;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import net.signalr.client.concurrent.Callback;
import net.signalr.client.concurrent.Futures;
import net.signalr.client.transports.NegotiationResponse;
import net.signalr.client.transports.Transport;

public class PersistentConnection implements Connection {

	private static final String PROTOCOL = "1.3";

	private static final long DEFAULT_ABORT_TIMEOUT = 30 * 1000;

	private final String _url;

	private final Transport _transport;

	private final Map<String, Collection<String>> _headers;

	private final Map<String, Collection<String>> _queryParameters;

	private String _protocol;

	private String _connectionToken;

	public PersistentConnection(String url, Transport transport) {
		if (url == null)
			throw new InvalidParameterException("URL must not be null");

		if (transport == null)
			throw new InvalidParameterException("Transport must not be null");

		_url = url;
		_transport = transport;
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
