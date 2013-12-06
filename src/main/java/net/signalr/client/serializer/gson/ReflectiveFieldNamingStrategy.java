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

package net.signalr.client.serializer.gson;

import java.lang.reflect.Field;

import net.signalr.client.serializer.SerializationException;
import net.signalr.client.serializer.SerializedName;

import com.google.gson.FieldNamingStrategy;

public final class ReflectiveFieldNamingStrategy implements FieldNamingStrategy {

	public String translateName(Field field) {
		SerializedName name = field.getAnnotation(SerializedName.class);

		if (name == null)
			throw new SerializationException("Annotation for serialized name is missing");

		return name.value();
	}
}
