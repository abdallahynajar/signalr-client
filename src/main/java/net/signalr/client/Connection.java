package net.signalr.client;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

public interface Connection {

	String getUrl();

	Map<String, Collection<String>> getHeaders();

	void addHeader(String name, String value);

	Map<String, Collection<String>> getQueryParameters();

	void addQueryParameter(String name, String value);

	String getProtocol();

	void setProtocol(String protocol);

	String getConnectionToken();
	
	void setConnectionToken(String connectionToken);
	
	Future<?> start(ConnectionListener listener);

	void stop();

	Future<?> send(String data);	
}
