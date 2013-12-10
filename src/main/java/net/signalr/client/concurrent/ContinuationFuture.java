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

public final class ContinuationFuture<I, O> implements Future<O> {

    private final Future<I> _future;

    private final Function<? super I, ? extends O> _function;

    public ContinuationFuture(final Future<I> future, final Function<? super I, ? extends O> function) {
        if (future == null)
            throw new InvalidParameterException("Future must not be null");

        if (function == null)
            throw new InvalidParameterException("Function must not be null");

        _future = future;
        _function = function;
    }

    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return _future.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return _future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return _future.isDone();
    }

    @Override
    public O get() throws InterruptedException, ExecutionException {
        final I input = _future.get();

        try {
            return _function.invoke(input);
        } catch (final Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public O get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        final I input = _future.get(timeout, unit);

        try {
            return _function.invoke(input);
        } catch (final Exception e) {
            throw new ExecutionException(e);
        }
    }
}
