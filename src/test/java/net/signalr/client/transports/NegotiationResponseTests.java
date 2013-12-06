package net.signalr.client.transports;

import net.signalr.client.serializers.Serializer;
import net.signalr.client.serializers.gson.GsonSerializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NegotiationResponseTests {

	private Serializer _serializer;
	
	@Before
	public void setUp() {
		_serializer = new GsonSerializer();
	}
	
	@Test
	public void deserializeTest() {
		String data = "{\"Url\":\"/signalr\",\"ConnectionToken\":\"Z1IuK7USZw4BwrDFbF8S+Hec4Mitkwe0+3N/FEZoQD8yVObSvtFdUfUJhKprVhjfXYlu1OLv2em/zMGN5ZK0Vr5H6ZqTvXaH+7Y8ee7yGjATfvZB\",\"ConnectionId\":\"0fa8593e-448e-4c98-9b21-2d95dce3adcc\",\"KeepAliveTimeout\":80.0,\"DisconnectTimeout\":120.0,\"TryWebSockets\":true,\"ProtocolVersion\":\"1.3\",\"TransportConnectTimeout\":5.0}";
		
		NegotiationResponse response = _serializer.deserialize(data, NegotiationResponse.class);
		
		Assert.assertEquals("/signalr", response.getUrl());
		Assert.assertEquals("Z1IuK7USZw4BwrDFbF8S+Hec4Mitkwe0+3N/FEZoQD8yVObSvtFdUfUJhKprVhjfXYlu1OLv2em/zMGN5ZK0Vr5H6ZqTvXaH+7Y8ee7yGjATfvZB", response.getConnectionToken());
		Assert.assertEquals("0fa8593e-448e-4c98-9b21-2d95dce3adcc", response.getConnectionId());
		Assert.assertEquals(new Double(80.0), response.getKeepAliveTimeout());
		Assert.assertEquals(120.0, response.getDisconnectTimeout(), 0.0);
		Assert.assertEquals(true, response.getTryWebSockets());
		Assert.assertEquals("1.3", response.getProtocolVersion());
		Assert.assertEquals(5.0, response.getTransportConnectTimeout(), 0.0);
	}
	
	@Test
	public void deserializeWithoutKeepAliveTimeoutTest() {
		String data = "{\"Url\":\"/signalr\",\"ConnectionToken\":\"Z1IuK7USZw4BwrDFbF8S+Hec4Mitkwe0+3N/FEZoQD8yVObSvtFdUfUJhKprVhjfXYlu1OLv2em/zMGN5ZK0Vr5H6ZqTvXaH+7Y8ee7yGjATfvZB\",\"ConnectionId\":\"0fa8593e-448e-4c98-9b21-2d95dce3adcc\",\"DisconnectTimeout\":120.0,\"TryWebSockets\":true,\"ProtocolVersion\":\"1.3\",\"TransportConnectTimeout\":5.0}";
		
		NegotiationResponse response =  _serializer.deserialize(data, NegotiationResponse.class);
		
		Assert.assertEquals("/signalr", response.getUrl());
		Assert.assertEquals("Z1IuK7USZw4BwrDFbF8S+Hec4Mitkwe0+3N/FEZoQD8yVObSvtFdUfUJhKprVhjfXYlu1OLv2em/zMGN5ZK0Vr5H6ZqTvXaH+7Y8ee7yGjATfvZB", response.getConnectionToken());
		Assert.assertEquals("0fa8593e-448e-4c98-9b21-2d95dce3adcc", response.getConnectionId());
		Assert.assertEquals(null, response.getKeepAliveTimeout());
		Assert.assertEquals(120.0, response.getDisconnectTimeout(), 0.0);
		Assert.assertEquals(true, response.getTryWebSockets());
		Assert.assertEquals("1.3", response.getProtocolVersion());
		Assert.assertEquals(5.0, response.getTransportConnectTimeout(), 0.0);
	}
}
