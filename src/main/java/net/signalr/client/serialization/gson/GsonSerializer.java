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

package net.signalr.client.serialization.gson;

import java.lang.reflect.Modifier;

import net.signalr.client.serialization.SerializationException;
import net.signalr.client.serialization.Serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonSerializer implements Serializer {

	private final Gson _gson;

	public GsonSerializer() {
		_gson = build();
	}

	private static Gson build() {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.excludeFieldsWithModifiers(Modifier.STATIC);
		gsonBuilder.setFieldNamingStrategy(new ReflectiveFieldNamingStrategy());

		return gsonBuilder.create();
	}

	public <T> String serialize(T graph) {
		try {
			return _gson.toJson(graph);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}

	public <T> T deserialize(String data, Class<T> clazz) {
		try {
			return _gson.fromJson(data, clazz);
		} catch (Exception e) {
			throw new SerializationException(e);
		}
	}
}
