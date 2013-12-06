package net.signalr.client.transports;

public final class NullTransportListener implements TransportListener {

	public void onOpen(Transport transport) {
	}
	
	public void onClose(Transport transport) {
	}

	public void onMessage(String message) {
	}
	
	public void onError(Transport transport, Throwable throwable) {
	}
}
