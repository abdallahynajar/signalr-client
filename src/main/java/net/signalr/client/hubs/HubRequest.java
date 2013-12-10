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

import java.util.Map;

import net.signalr.client.serialization.Name;

public final class HubRequest {

    @Name("I")
    private String _callbackId;

    @Name("H")
    private String _hubName;

    @Name("M")
    private String _methodName;

    @Name("A")
    private Object[] _arguments;

    @Name("S")
    private Map<String, Object> _state;

    public void setCallbackId(String callbackId) {
        _callbackId = callbackId;
    }

    public void setHubName(String hubName) {
        _hubName = hubName;
    }

    public void setMethodName(String methodName) {
        _methodName = methodName;
    }

    public void setArguments(Object[] arguments) {
        _arguments = arguments;
    }

    public void setState(Map<String, Object> state) {
        _state = state;
    }
}
