package run.halo.starter.model;

import lombok.Data;
import run.halo.app.core.extension.content.Post;
import run.halo.app.extension.Ref;

@Data
public class PostRequest {


    private Post post;
    private Content content;

    public PostRequest(Post post, Content content) {
        this.post = post;
        this.content = content;
    }

    public ContentRequest contentRequest() {
        Ref subjectRef = Ref.of(post);
        return new ContentRequest(subjectRef, post.getSpec().getHeadSnapshot(), content.getRaw(),
            content.getContent(), content.getRawType());
    }
}
