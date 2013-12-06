package net.signalr.client.hubs;

import net.signalr.client.PersistentConnection;
import net.signalr.client.concurrent.Callback;
import net.signalr.client.transports.Transport;

public final class HubConnection extends PersistentConnection {

	public HubConnection(String url, Transport transport) {
		super(url, transport);
	}

	public HubProxy createHubProxy(String hubName) {
		return null;
	}

	public String registerCallback(Callback<HubResponse, Void> callback) {
		return null;
	}

	public void removeCallback(String callbackId) {

	}
}
