package com.krld.synapsex;

import com.krld.synapsex.utils.MultipleFutures;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;

public class MainVerticle extends BaseVerticle {
    private List<String> deploymentIds = new ArrayList<>();

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        super.start(startFuture);
        System.out.println("Hello World");
        MultipleFutures mf = new MultipleFutures();
        mf.add(v -> deployVerticle(SyncVerticle.class.getName(), v));
        mf.add(v -> deployVerticle(SyncVerticle.class.getName(), v));
        mf.add(v -> deployVerticle(SyncVerticle.class.getName(), v));
        mf.setHandler(event -> {
            if (event.failed()) {
                event.cause().printStackTrace();
                System.exit(1);
                return;
            }
            deployVerticle(IdGeneratorVerticle.class.getName(),
                    Future.future().setHandler(v -> {
                        for (int x = 0; x < 8; x++) {
                            deployVerticle(WebApiVerticle.class.getName(), Future.future());
                        }
                    })
            );
        });
        mf.start();
    }

    private <T> void deployVerticle(String name, Future<T> future) {
        DeploymentOptions options = new DeploymentOptions();
        vertx.deployVerticle(name, options, result -> {
            if (result.failed()) {
                future.fail(result.cause());
            } else {
                deploymentIds.add(result.result());
                future.complete();
            }
        });
    }
}
