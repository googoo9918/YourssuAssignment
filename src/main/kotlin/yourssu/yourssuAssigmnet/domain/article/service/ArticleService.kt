package yourssu.yourssuAssigmnet.domain.article.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yourssu.yourssuAssigmnet.domain.article.entity.Article
import yourssu.yourssuAssigmnet.domain.article.repository.ArticleRepository
import yourssu.yourssuAssigmnet.domain.user.service.UserService
import yourssu.yourssuAssigmnet.global.error.ErrorCode
import yourssu.yourssuAssigmnet.global.error.exception.EntityNotFoundException

@Service
@Transactional
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val userService: UserService
) {
    fun createArticle(email: String, article: Article): Article {
//        val validatedUser = userService.validateRegisterUser(email, password)
        article.user = userService.findUserByEmail(email)
        return articleRepository.save(article)
    }

    fun updateArticle(articleId: Long, email: String, article: Article): Article {
        val preArticle = findVerifiedArticeByArticeId(articleId)
//        userService.validateUserAndAuthor(email, password, preArticle.user)
        userService.validateAuthor(userService.findUserByEmail(email), preArticle.user)
        article.title?.let { preArticle.updateTitle(it) }
        article.content?.let { preArticle.updateContent(it) }

        return articleRepository.save(preArticle)
    }

    fun deleteArticle(articleId: Long, email: String) {
        val article = findVerifiedArticeByArticeId(articleId)
        //        userService.validateUserAndAuthor(email, password, preArticle.user)
        userService.validateAuthor(userService.findUserByEmail(email), article.user)
        articleRepository.deleteById(articleId)
    }

    fun findVerifiedArticeByArticeId(articleId: Long): Article {
        return articleRepository.findById(articleId).orElseThrow { EntityNotFoundException(ErrorCode.ARTICLE_NOT_EXISTS) }
    }
}
