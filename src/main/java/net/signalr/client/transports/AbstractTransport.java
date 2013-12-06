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
