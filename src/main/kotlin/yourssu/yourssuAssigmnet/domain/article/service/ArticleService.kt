package yourssu.yourssuAssigmnet.domain.article.service

import yourssu.yourssuAssigmnet.domain.article.entity.Article
import yourssu.yourssuAssigmnet.domain.article.repository.ArticleRepository
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userService: UserService
) {
    fun createArticle(email: String, password: String, article: Article): Article {
        val validatedUser = userService.validateRegisterUser(email, password)
        article.user = validatedUser
        return articleRepository.save(article)
    }

    fun updateArticle(articleId: Long, email: String, password: String, article: Article): Article {
        val preArticle = findVerifiedArticeByArticeId(articleId)
        userService.validateUserAndAuthor(email, password, preArticle.user)

        article.title?.let { preArticle.updateTitle(it) }
        article.content?.let { preArticle.updateContent(it) }

        return articleRepository.save(preArticle)
    }

    fun deleteArticle(articleId: Long, email: String, password: String) {
        val article = findVerifiedArticeByArticeId(articleId)
        userService.validateUserAndAuthor(email, password, article.user)
        articleRepository.deleteById(articleId)
    }

    fun findVerifiedArticeByArticeId(articleId: Long): Article {
        return articleRepository.findById(articleId).orElseThrow { EntityNotFoundException(ErrorCode.ARTICLE_NOT_EXISTS) }
    }
}
