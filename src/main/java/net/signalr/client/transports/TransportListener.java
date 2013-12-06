package net.signalr.client.transports;

public interface TransportListener {

	void onOpen(Transport transport);

	void onClose(Transport transport);

	void onMessage(String message);

	void onError(Transport transport, Throwable throwable);
}
