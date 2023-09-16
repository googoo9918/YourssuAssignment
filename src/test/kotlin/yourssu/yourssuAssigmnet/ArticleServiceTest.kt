package yourssu.yourssuAssigmnet

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import yourssu.yourssuAssigmnet.domain.article.entity.Article
import yourssu.yourssuAssigmnet.domain.article.repository.ArticleRepository
import yourssu.yourssuAssigmnet.domain.article.service.ArticleService
import yourssu.yourssuAssigmnet.domain.user.entity.User
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import java.util.*
import javax.persistence.EntityNotFoundException

@ExtendWith(MockitoExtension::class)
class ArticleServiceTest {

    @Mock
    lateinit var articleRepository: ArticleRepository

    @Mock
    lateinit var userService: UserService

    @InjectMocks
    lateinit var articleService: ArticleService

    @Test
    fun `test createArticle with valid user`() {
        val email = "email@urssu.com"
        val password = "password"
        val article = Article(content = "content", title = "title")
        val user = User(email = email, password = password, username = "username")

        `when`(userService.validateRegisterUser(email, password)).thenReturn(user)
        `when`(articleRepository.save(any())).thenReturn(article)

        val result = articleService.createArticle(email, password, article)

        assertEquals(result, article)
        assertEquals(result.user, user)
    }

    @Test
    fun `test updateArticle with validate author`() {
        val articleId = 1L
        val email = "email@urssu.com"
        val password = "password"
        val article = Article(content = "updated content", title = "updated title")
        val preArticle = Article(content = "content", title = "title", user = User(email = email, password = password, username = "username"))

        `when`(articleRepository.findById(articleId)).thenReturn(Optional.of(preArticle))
        `when`(userService.validateUserAndAuthor(email, password, preArticle.user)).thenReturn(preArticle.user!!)
        `when`(articleRepository.save(any())).thenReturn(preArticle)

        val result = articleService.updateArticle(articleId, email, password, article)

        assertEquals(result.title, article.title)
        assertEquals(result.content, article.content)
    }

    @Test
    fun `test updateArticle with non-matching author`() {
        val articleId = 1L
        val email = "email@urssu.com"
        val password = "password"
        val article = Article(content = "updated content", title = "updated title")
        val preArticle = Article(content = "content", title = "title", user = User(email = "email2@yourssu.com", password = password, username = "username2"))

        `when`(articleRepository.findById(articleId)).thenReturn(Optional.of(preArticle))

        assertThrows<EntityNotFoundException> {
            articleService.updateArticle(articleId, email, password, article)
        }
    }

    @Test
    fun `test deleteArticle with valid user`() {
        val articleId = 1L
        val email = "email@urssu.com"
        val password = "password"
        val article = Article(content = "content", title = "title", user = User(email = email, password = password, username = "username"))

        `when`(articleRepository.findById(articleId)).thenReturn(Optional.of(article))
        `when`(userService.validateUserAndAuthor(email, password, article.user)).thenReturn(article.user!!)

        articleService.deleteArticle(articleId, email, password)

        verify(articleRepository).deleteById(articleId)
    }
}