package yourssu.yourssuAssigmnet.domain.article.controller

import yourssu.yourssuAssigmnet.domain.article.service.ArticleService
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.article.dto.ArticleDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import yourssu.yourssuAssigmnet.domain.article.mapper.ArticleMapper
import yourssu.yourssuAssigmnet.global.resolver.authinfo.Auth
import yourssu.yourssuAssigmnet.global.resolver.authinfo.AuthInfo
import javax.validation.Valid
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/api/articles")
@Validated
class ArticleController(
    private val articleService: ArticleService,
    private val articleMapper: ArticleMapper
) {
    @PostMapping
    fun postArticle(@Auth authInfo: AuthInfo,
                    @Valid @RequestBody articlePostDto: ArticleDto.Input): ResponseEntity<ArticleDto.Response> {
        val article = articleService.createArticle(
            authInfo.email,
            articleMapper.articleInputDtoToArticle(articlePostDto)
        )
        val response = articleMapper.articleToArticleResponse(article, authInfo.email)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{articleId}")
    fun updateArticle(
        @PathVariable @Positive articleId: Long,
        @Auth authInfo: AuthInfo,
        @Valid @RequestBody articlePatchDto: ArticleDto.Input
    ): ResponseEntity<ArticleDto.Response> {
        val article = articleService.updateArticle(
            articleId, authInfo.email,
            articleMapper.articleInputDtoToArticle(articlePatchDto)
        )
        val response = articleMapper.articleToArticleResponse(article, authInfo.email)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{articleId}")
    fun deleteArticle(
        @PathVariable @Positive articleId: Long,
        @Auth authInfo: AuthInfo,
        @Valid @RequestBody articleDeleteDto: BaseUserDto
    ): ResponseEntity<Void> {
        articleService.deleteArticle(articleId, authInfo.email)
        return ResponseEntity.ok().build()
    }
}
