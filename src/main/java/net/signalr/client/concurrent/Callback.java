package net.signalr.client.concurrent;

public interface Callback<V, R> {
	R invoke(V value);
}
