package com.krld.synapsex;

import com.google.gson.Gson;
import com.krld.synapsex.utils.JsonUtils;
import io.vertx.core.AbstractVerticle;

public class BaseVerticle extends AbstractVerticle{

    private Gson gson = JsonUtils.getGson(false);;

    protected void sendEventBus(String address, Object obj) {
        vertx.eventBus().send(address, gson.toJson(obj));
    }
}
