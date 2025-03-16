package run.halo.starter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import run.halo.starter.model.PostRequest;
import run.halo.starter.service.BlogSynchronizer;
import java.io.IOException;
@Slf4j
@Component
public class AliCloudSynchronizer  extends ParentSynchronizer implements BlogSynchronizer {

    @Override
    public PostRequest parse(String url) throws IOException {
        PostRequest postRequest;
        Document doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .timeout(10000)
            .get();
        String title = doc.selectFirst("h1.article-title").text();

        StringBuilder content = new StringBuilder();
        Elements paragraphs = doc.select("div.article-content p");
        for (Element p : paragraphs) {
            content.append(p.text()).append("\n");
        }


        postRequest = getPostRequest(title, content.toString());
        return postRequest;
    }
}
