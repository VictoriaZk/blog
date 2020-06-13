package repository;

import com.leverx.blog.model.Article;
import com.leverx.blog.repository.ArticleRepository;
import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void deleteArticle() {
        articleRepository.delete(2);
        Optional<Article> deleteArticle = articleRepository.findById(2);
        Assert.assertFalse(deleteArticle.isPresent());
    }

}
