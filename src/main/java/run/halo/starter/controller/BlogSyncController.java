package run.halo.starter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import run.halo.app.plugin.ApiVersion;
import run.halo.starter.service.BlogSynchronizer;
import run.halo.starter.service.SynchronizerFactory;
import run.halo.starter.model.SyncRequest;
import run.halo.starter.model.SyncResult;

@ApiVersion("v1alpha1")
@RestController
@RequestMapping("/blogSync")
@RequiredArgsConstructor
@Slf4j
public class BlogSyncController {
// /apis/api.plugin.halo.run/v1alpha1/plugins/blog-sync/blogSync/**

    private final SynchronizerFactory parserFactory;

    @PostMapping(value = "/sync",consumes = {})
    public SyncResult<String> sync(@CookieValue("XSRF-TOKEN") String token,
        @CookieValue("SESSION") String session,
        @RequestBody SyncRequest request,
        ServerWebExchange exchange
        ) {
        String origin = exchange.getRequest().getHeaders().getOrigin();
        BlogSynchronizer synchronizer = parserFactory.getSynchronizer(request.getType());
        log.info("Sync  origin: {}, request: {}",origin, request);
        return synchronizer.sync(request.getUrl(),token,session,origin);
    }

}