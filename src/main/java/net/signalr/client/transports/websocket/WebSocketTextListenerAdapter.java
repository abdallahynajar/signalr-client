package net.signalr.client.transports.websocket;

import java.security.InvalidParameterException;

import com.ning.http.client.websocket.WebSocket;
import com.ning.http.client.websocket.WebSocketTextListener;

public final class WebSocketTextListenerAdapter implements WebSocketTextListener {

	private final WebSocketTransport _transport;

	public WebSocketTextListenerAdapter(WebSocketTransport transport) {
		if (transport == null)
			throw new InvalidParameterException("Transport must not be null");

		_transport = transport;
	}

	public void onOpen(WebSocket webSocket) {
		_transport.onOpen();
	}

	public void onClose(WebSocket webSocket) {
		_transport.onClose();
	}

	public void onFragment(String fragment, boolean last) {
	}

	public void onMessage(String message) {
		_transport.onMessage(message);
	}

	public void onError(Throwable throwable) {
		_transport.onError(throwable);
	}
}
