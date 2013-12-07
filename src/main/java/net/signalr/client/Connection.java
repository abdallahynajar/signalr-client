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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

import net.signalr.client.serializers.Serializer;

public interface Connection {

	String getUrl();

	Map<String, Collection<String>> getHeaders();

	void addHeader(String name, String value);

	Map<String, Collection<String>> getQueryParameters();

	void addQueryParameter(String name, String value);

	String getProtocol();

	void setProtocol(String protocol);

	String getConnectionToken();

	void setConnectionToken(String connectionToken);

	Serializer getSerializer();

	Future<?> start(ConnectionListener listener);

	void stop();

	Future<?> send(String data);
}
