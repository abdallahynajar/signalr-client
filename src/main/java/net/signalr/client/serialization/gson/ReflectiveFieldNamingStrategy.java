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

import java.lang.reflect.Field;

import net.signalr.client.serialization.SerializationException;
import net.signalr.client.serialization.Name;

import com.google.gson.FieldNamingStrategy;

public final class ReflectiveFieldNamingStrategy implements FieldNamingStrategy {

	public String translateName(final Field field) {
		final Name name = field.getAnnotation(Name.class);

		if (name == null)
			throw new SerializationException("Name annotation is missing");

		return name.value();
	}
}
