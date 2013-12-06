/*
 * Copyright � Martin Tamme
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

import net.signalr.client.serializers.SerializedName;

public final class HubResponse {

	/**
	 * The callback identifier.
	 */
	@SerializedName("I")
	private String _id;

	/**
	 * The return value of the hub
	 */
	@SerializedName("R")
	private String _result;

	/**
	 * Indicates whether the Error is a <code>HubException</code>.
	 */
	@SerializedName("H")
	private Boolean _isHubException;

	/**
	 * The error message returned from the hub invocation.
	 */
	@SerializedName("E")
	private String _errorMessage;

	/**
	 * The error data returned from the hub invocation.
	 */
	@SerializedName("D")
	private Object _errorData;

	/**
	 * The caller state from this hub.
	 */
	@SerializedName("S")
	private Map<String, String> _state;
}
