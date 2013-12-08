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

public final class HubResponse {

	/**
	 * The changes made the the round tripped state.
	 */
	@Name("S")
	private Map<String, Object> _state;

	/**
	 * The result of the invocation.
	 */
	@Name("R")
	private Object _result;

	/**
	 * The ID of the operation.
	 */
	@Name("I")
	private String _callbackId;

	/**
	 * Indicates whether the error is a <code>HubException</code>.
	 */
	@Name("H")
	private Boolean _isHubException;

	/**
	 * The exception that occurs as a result of invoking the hub method.
	 */
	@Name("E")
	private String _error;

	/**
	 * The stack trace of the exception that occurs as a result of invoking the
	 * hub method.
	 */
	@Name("T")
	private String _stackTrace;

	/**
	 * Extra error data contained in the <code>HubException</code>.
	 */
	@Name("D")
	private String _errorData;
	
	@Name("C")
	private String _messageId;
	
	public String getCallbackId() {
		return _callbackId;
	}
	
	public String getMessageId() {
		return _messageId;
	}
}
