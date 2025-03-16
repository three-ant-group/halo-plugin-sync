package run.halo.starter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import run.halo.starter.model.PostRequest;
import run.halo.starter.service.BlogSynchronizer;
import java.io.IOException;

@Slf4j
@Component
public class CsdnSynchronizer  extends ParentSynchronizer implements BlogSynchronizer {


    @Override
    public PostRequest parse(String url) throws IOException {
        PostRequest postRequest;
        Document doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get();
        String title = doc.selectFirst("#articleContentId").text();
        String content = doc.selectFirst("#content_views").html();

        postRequest = getPostRequest(title, content);
        return postRequest;
    }

}