package com.krld.synapsex;

import io.vertx.core.Vertx;

public class Runner {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName());
    }
}
