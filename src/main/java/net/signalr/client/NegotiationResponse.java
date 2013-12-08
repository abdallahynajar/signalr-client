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

import net.signalr.client.serialization.Name;

/**
 * Reprsesents a negotiation response.
 * 
 * @author mtamme
 */
public final class NegotiationResponse {

	@Name("Url")
	private String _url;

	@Name("ConnectionToken")
	private String _connectionToken;

	@Name("ConnectionId")
	private String _connectionId;

	@Name("ProtocolVersion")
	private String _protocolVersion;

	@Name("TryWebSockets")
	private boolean _tryWebSockets;

	@Name("KeepAliveTimeout")
	private Double _keepAliveTimeout;

	@Name("DisconnectTimeout")
	private double _disconnectTimeout;

	@Name("TransportConnectTimeout")
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
