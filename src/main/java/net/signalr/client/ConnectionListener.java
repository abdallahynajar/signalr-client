package net.signalr.client;

public interface ConnectionListener {

	void onReceived(String data);
	
    void onError(Throwable throwable);
    
    void onReconnecting();
    
    void onReconnected();
    
    void onConnectionSlow();
}
