package com.krld.synapsex;

import com.krld.synapsex.models.Event;
import io.vertx.core.Future;

public class WebApiVerticle extends BaseVerticle {
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        vertx.setPeriodic(33, event -> {
            Event e = new Event();
            e.body = "Hello";
            e.type = "m.room.message";
            sendEventBus(Addresses.INCOMING_EVENT, e);
        });
    }

}
