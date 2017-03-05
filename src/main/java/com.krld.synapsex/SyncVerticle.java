package com.krld.synapsex;

import io.vertx.core.Future;

public class SyncVerticle extends BaseVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        vertx.eventBus().consumer(Addresses.NOTIFY,
                event -> {
                });

        startFuture.succeeded();
    }
}
