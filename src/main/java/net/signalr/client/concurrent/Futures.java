package net.signalr.client.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public final class Futures {

	public static <S, R> Future<R> then(final Future<S> future, final Callback<S, R> successor) {

		return new FutureTask<R>(new Callable<R>() {
			public R call() throws Exception {
				S result = future.get();

				return successor.invoke(result);
			}
		});
	}
}
