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
public class JianShuSynchronizer  extends ParentSynchronizer implements BlogSynchronizer {
    @Override
    public PostRequest parse(String url) throws IOException {
        PostRequest postRequest;
        Document doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get();
        String title = doc.selectFirst("._1RuRku").text();
        String content = doc.selectFirst("._2rhmJa").html();

        postRequest = getPostRequest(title, content);
        return postRequest;
    }
}
