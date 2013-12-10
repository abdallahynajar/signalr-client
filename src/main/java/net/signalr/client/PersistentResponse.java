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

public class PersistentResponse {

    @Name("C")
    private String _messageId;

    @Name("S")
    private Integer _initialize;

    @Name("D")
    private Integer _disconnect;

    @Name("T")
    private Integer _reconnect;

    @Name("G")
    private String _groupsToken;

    @Name("L")
    private long _longPollDelay;

    @Name("M")
    private Object[] _messages;

    public String getMessageId() {
        return _messageId;
    }

    public boolean isInitialize() {
        return (_initialize == 1);
    }

    public boolean isDisconnect() {
        return (_disconnect == 1);
    }

    public boolean isReconnect() {
        return (_reconnect == 1);
    }

    public String getGroupsToken() {
        return _groupsToken;
    }
}
