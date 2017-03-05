package com.krld.synapsex;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class Runner {
    private static String LOG_TAG = "Runner";

    public static void main(String[] args) {
        cluster();
        //nonCluster();
    }

    private static void cluster() {
        VertxOptions options = new VertxOptions();

        options.setClusterManager(new HazelcastClusterManager());
        options.setClusterHost("192.168.0.5"); //TODO extract cluster from here
        Vertx.clusteredVertx(options, event -> {
            if (event.failed()) {
                event.cause().printStackTrace();
            } else {
                Vertx vertx = event.result();
                vertx.eventBus().send(Addresses.IS_RUNNING, "", e -> {
                    if (e.failed()) {
                        System.out.println(LOG_TAG + " Deploy MainVerticle");
                        vertx.deployVerticle(MainVerticle.class.getName());
                    } else {
                        System.out.println(LOG_TAG + " Done");
                        for (int x = 0; x < 8; x++) {
                            vertx.deployVerticle(WebApiVerticle.class.getName());
                            vertx.deployVerticle(SyncVerticle.class.getName());
                        }
                        System.out.println(LOG_TAG + " deployed 8 WebApiVerticles and 8 SyncVerticles");
                    }
                });
            }
        });
    }

    private static void nonCluster() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(MainVerticle.class.getName());
    }
}
