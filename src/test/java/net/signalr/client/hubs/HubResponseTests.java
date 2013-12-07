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

import net.signalr.client.serialization.Serializer;
import net.signalr.client.serialization.gson.GsonSerializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class HubResponseTests {

	private Serializer _serializer;

	@Before
	public void setUp() {
		_serializer = new GsonSerializer();
	}

	@Test
	public void deserializeTest() {
		String data = "";

		HubResponse response = _serializer.deserialize(data, HubResponse.class);

		Assert.assertNotNull(response);
	}
}
