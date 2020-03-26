package mops.foren.applicationservices;

import mops.foren.domain.model.ForumId;
import mops.foren.domain.model.Post;
import mops.foren.domain.model.PostId;
import mops.foren.domain.model.ThreadId;
import mops.foren.domain.model.paging.PostPage;
import mops.foren.domain.repositoryabstraction.IPostRepository;
import mops.foren.domain.services.ThreadModelService;

@ApplicationService
public class PostService {

    private IPostRepository postRepository;
    private ThreadModelService threadModelService;

    public PostService(IPostRepository postRepository, ThreadModelService threadModelService) {
        this.postRepository = postRepository;
        this.threadModelService = threadModelService;
    }

    public PostPage getPosts(ThreadId threadId, Integer page) {
        return this.postRepository.getPostPageFromDB(threadId, page);
    }

    public Post getPost(PostId postId) {
        return this.postRepository.getPostById(postId);
    }


    /**
     * This method should delete a post.
     * This method also updates the lastPostTime changed in Threads.
     *
     * @param post The post that should be deleted.
     */
    public void deletePost(Post post) {
        this.postRepository.deletePostById(post.getId());
    }

    public PostPage searchWholeForum(ForumId forumId, String content, Integer page) {
        return this.postRepository.searchWholeForumForContent(forumId, content, page);
    }

    public void setPostVisible(PostId postId) {
        this.postRepository.setPostVisible(postId);
    }
}
