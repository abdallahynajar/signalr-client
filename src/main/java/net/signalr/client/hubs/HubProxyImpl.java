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

import java.util.concurrent.Future;

public final class HubProxyImpl implements HubProxy {

    public HubProxyImpl(final HubConnection connection, final String hubName) {
    }

    public <T> Future<T> invoke(final String method, final Object... args) {
        // TODO Auto-generated method stub
        return null;
    }

    public void subscribe(final String eventName, final HubListener listener) {
        // TODO Auto-generated method stub
    }
}
