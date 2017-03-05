package com.krld.synapsex;

import com.google.gson.Gson;
import com.krld.synapsex.models.Event;
import com.krld.synapsex.utils.JsonUtils;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;

public class SyncVerticle extends BaseVerticle {

    private Gson gson = JsonUtils.getGson(false);
    private long lastId;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus().consumer(Addresses.NOTIFY, this::onNotify);

        vertx.setPeriodic(2000, event -> System.out.println(deploymentID() + " lastId: " + lastId));

        startFuture.complete();
    }

    private void onNotify(Message<String> msg) {
        Event e = gson.fromJson(msg.body(), Event.class);

        if (e.eventId < lastId) {
            System.err.println("Error! received event in wrong order!!");
        } else if (lastId != 0 && lastId + 1 != e.eventId ) {
            System.err.println("Error! detected skipped event " + e.eventId);
        } else {
            lastId = e.eventId;
        }
    }
}
