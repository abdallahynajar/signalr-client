/*
 * Copyright © Martin Tamme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.signalr.client;

import net.signalr.client.NegotiationResponse;
import net.signalr.client.serialization.Serializer;
import net.signalr.client.serialization.gson.GsonSerializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class NegotiationResponseTests {

	private Serializer _serializer;

	@Before
	public void setUp() {
		_serializer = new GsonSerializer();
	}

	@Test
	public void deserializeTest() {
		String data = "{\"Url\":\"/signalr\",\"ConnectionToken\":\"Z1IuK7USZw4BwrDFbF8S+Hec4Mitkwe0+3N/FEZoQD8yVObSvtFdUfUJhKprVhjfXYlu1OLv2em/zMGN5ZK0Vr5H6ZqTvXaH+7Y8ee7yGjATfvZB\",\"ConnectionId\":\"0fa8593e-448e-4c98-9b21-2d95dce3adcc\",\"KeepAliveTimeout\":80.0,\"DisconnectTimeout\":120.0,\"TryWebSockets\":true,\"ProtocolVersion\":\"1.3\",\"TransportConnectTimeout\":5.0}";

		NegotiationResponse response = _serializer.deserialize(data, NegotiationResponse.class);

		Assert.assertNotNull(response);
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

		NegotiationResponse response = _serializer.deserialize(data, NegotiationResponse.class);

		Assert.assertNotNull(response);
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
