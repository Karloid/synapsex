package com.krld.synapsex;

import io.vertx.core.Future;

public class IdGeneratorVerticle extends BaseVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        //TODO consume new event

        startFuture.succeeded();
    }
}
