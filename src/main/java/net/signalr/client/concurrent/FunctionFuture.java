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

package net.signalr.client.concurrent;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class FunctionFuture<I, O> implements Future<O> {

    private final Function<? super I, ? extends O> _function;

    private final I _input;

    private volatile boolean _isDone;

    public FunctionFuture(final Function<? super I, ? extends O> function, final I input) {
        if (function == null)
            throw new InvalidParameterException("Function must not be null");

        _function = function;
        _input = input;

        _isDone = false;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return _isDone;
    }

    @Override
    public O get() throws InterruptedException, ExecutionException {
        try {
            return _function.invoke(_input);
        } catch (Exception e) {
            throw new ExecutionException(e);
        } finally {
            _isDone = true;
        }
    }

    @Override
    public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return get();
    }
}
