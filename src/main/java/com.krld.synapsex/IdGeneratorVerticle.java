package com.krld.synapsex;

import com.google.gson.Gson;
import com.krld.synapsex.models.Event;
import com.krld.synapsex.utils.JsonUtils;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;

public class IdGeneratorVerticle extends BaseVerticle {

    private Gson gson = JsonUtils.getGson(false);
    private long id;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);

        //TODO consume new event
        vertx.eventBus().consumer(Addresses.INCOMING_EVENT, this::processIncomingEvent);

        startFuture.succeeded();
    }

    private void processIncomingEvent(Message<String> event) {
        Event e = gson.fromJson(event.body(), Event.class);
        e.eventId = ++id;
        vertx.eventBus().publish(Addresses.NOTIFY, gson.toJson(e));
    }
}
