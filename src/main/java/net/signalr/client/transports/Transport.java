package net.signalr.client.transports;

import java.util.concurrent.Future;

import net.signalr.client.Connection;

public interface Transport {
	
	void setTransportListener(TransportListener listener);
	
	Future<NegotiationResponse> negotiate(Connection connection, String connectionData);
	
	Future<?> start(Connection connection, String connectionData);
	
	Future<?> send(Connection connection, String connectionData, String data);
	
	void abort(Connection connection, long timeout, String connectionData);
}
