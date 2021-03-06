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

package net.signalr.client.hubs;

import net.signalr.client.PersistentConnection;
import net.signalr.client.concurrent.Function;
import net.signalr.client.serialization.Serializer;
import net.signalr.client.transports.Transport;

public final class HubConnection extends PersistentConnection {

    public HubConnection(final String url, final Transport transport, final Serializer serializer) {
        super(url, transport, serializer);
    }

    public HubProxy createHubProxy(final String hubName) {
        return new HubProxyImpl(this, hubName);
    }

    public String registerCallback(final Function<HubResponse, Void> callback) {
        return null;
    }

    public void removeCallback(final String callbackId) {

    }
}
