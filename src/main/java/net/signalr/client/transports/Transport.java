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

import java.util.concurrent.Future;

import net.signalr.client.Connection;

/**
 * Defines a transport.
 */
public interface Transport {

    /**
     * Returns the transport name.
     * 
     * @return the transport name.
     */
    String getName();

    void addTransportListener(TransportListener listener);

    void removeTransportListener(TransportListener listener);

    Future<String> negotiate(Connection connection, String connectionData);

    Future<Session> start(Connection connection, String connectionData);

    Future<Void> abort(Connection connection, String connectionData);
}
