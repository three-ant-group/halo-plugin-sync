package run.halo.starter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import run.halo.starter.model.PostRequest;
import run.halo.starter.service.BlogSynchronizer;

@Slf4j
@Component
public class CnblogsSynchronizer extends ParentSynchronizer implements BlogSynchronizer {

    @Override
    public PostRequest parse(String url) {
        return null;
    }
}