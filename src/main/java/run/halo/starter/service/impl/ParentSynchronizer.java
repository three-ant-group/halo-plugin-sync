package run.halo.starter.service.impl;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.markdown4j.Markdown4jProcessor;
import run.halo.app.core.extension.content.Post;
import run.halo.app.extension.Metadata;
import run.halo.starter.model.Content;
import run.halo.starter.model.PostRequest;
import java.io.IOException;

@Slf4j
public class ParentSynchronizer
{

    protected PostRequest getPostRequest(String title, String content) throws IOException {
        Post post = new Post();

        Post.PostSpec postSpec = new Post.PostSpec();
        postSpec.setTitle(title);
        postSpec.setSlug(UUID.fastUUID().toString(false));
        postSpec.setAllowComment(true);
        postSpec.setDeleted(false);
        Post.Excerpt excerpt = new Post.Excerpt();
        excerpt.setAutoGenerate(true);
        postSpec.setExcerpt(excerpt);
        postSpec.setPriority(0);
        postSpec.setVisible(Post.VisibleEnum.PUBLIC);
        postSpec.setPublish(false);
        postSpec.setPinned(false);

        Post.PostStatus postStatus = new Post.PostStatus();
        //草稿箱，待发布状态
        postStatus.setPhase(Post.PostPhase.DRAFT.name());
        // postStatus.setContributors(List.of(owner));

        post.setSpec(postSpec);
        post.setStatus(postStatus);
        //设置元数据才能保存
        post.setMetadata(new Metadata());
        post.getMetadata().setName(UUID.fastUUID().toString(false));

        PostRequest postRequest = new PostRequest(post, new Content(content,
            new Markdown4jProcessor().process(content),
            "html")
        );

        return postRequest;
    }

}
