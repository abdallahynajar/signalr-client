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

import net.signalr.client.serialization.Serializer;
import net.signalr.client.serialization.gson.GsonSerializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class PersistentResponseTests {

    private Serializer _serializer;

    @Before
    public void setUp() {
        _serializer = new GsonSerializer();
    }

    @Test
    public void deserializeInitializationResponseTest() {
        String data = "{\"C\":\"s-0,298F386\",\"S\":1,\"M\":[]}";

        PersistentResponse response = _serializer.deserialize(data,
                PersistentResponse.class);

        Assert.assertNotNull(response);
        Assert.assertEquals("s-0,298F386", response.getMessageId());
        Assert.assertEquals(true, response.isInitialize());
    }

    @Test
    public void deserializeGroupTokenResponseTest() {
        String data = "{\"C\":\"s-0,298F388\",\"G\":\"jFN2mJ5rvg9vPfwkBxM1YlE6xggh6C+h+RfCKioW0uJpH0vg3bL40vD2e4p8Ncr4vsrTxzqDKN7zBqCUclpqEgzuJRwG/mKifZrTcxdLez2DMF8ZmGTi0/N6vBju1XQVGnMj3HpOKDieWe8ifbFTL89lIFg=\",\"M\":[]}";

        PersistentResponse response = _serializer.deserialize(data,
                PersistentResponse.class);

        Assert.assertNotNull(response);
        Assert.assertEquals("s-0,298F388", response.getMessageId());
        Assert.assertEquals(false, response.isInitialize());
        Assert.assertEquals(
                "jFN2mJ5rvg9vPfwkBxM1YlE6xggh6C+h+RfCKioW0uJpH0vg3bL40vD2e4p8Ncr4vsrTxzqDKN7zBqCUclpqEgzuJRwG/mKifZrTcxdLez2DMF8ZmGTi0/N6vBju1XQVGnMj3HpOKDieWe8ifbFTL89lIFg=",
                response.getGroupsToken());
    }
}
