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

package net.signalr.client.transports;

import net.signalr.client.serializers.SerializedName;

public final class NegotiationResponse {

	@SerializedName("Url")
	private String _url;

	@SerializedName("ConnectionToken")
	private String _connectionToken;

	@SerializedName("ConnectionId")
	private String _connectionId;

	@SerializedName("ProtocolVersion")
	private String _protocolVersion;

	@SerializedName("TryWebSockets")
	private boolean _tryWebSockets;

	@SerializedName("KeepAliveTimeout")
	private Double _keepAliveTimeout;

	@SerializedName("DisconnectTimeout")
	private double _disconnectTimeout;

	@SerializedName("TransportConnectTimeout")
	private double _transportConnectTimeout;

	public String getUrl() {
		return _url;
	}

	public String getConnectionToken() {
		return _connectionToken;
	}

	public String getConnectionId() {
		return _connectionId;
	}

	public String getProtocolVersion() {
		return _protocolVersion;
	}

	public boolean getTryWebSockets() {
		return _tryWebSockets;
	}

	public double getDisconnectTimeout() {
		return _disconnectTimeout;
	}

	public Double getKeepAliveTimeout() {
		return _keepAliveTimeout;
	}

	public double getTransportConnectTimeout() {
		return _transportConnectTimeout;
	}
}
