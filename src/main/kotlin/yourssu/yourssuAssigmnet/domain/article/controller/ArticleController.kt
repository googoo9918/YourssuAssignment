package yourssu.yourssuAssigmnet.domain.article.controller

import yourssu.yourssuAssigmnet.domain.article.service.ArticleService
import yourssu.yourssuAssigmnet.domain.common.dto.BaseUserDto
import yourssu.yourssuAssigmnet.domain.article.dto.ArticleDto
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import yourssu.yourssuAssigmnet.domain.article.mapper.ArticleMapper
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
    fun postArticle(@Valid @RequestBody articlePostDto: ArticleDto.Input): ResponseEntity<ArticleDto.Response> {
        val article = articleService.createArticle(
            articlePostDto.email, articlePostDto.password,
            articleMapper.articleInputDtoToArticle(articlePostDto)
        )
        val response = articleMapper.articleToArticleResponse(article, articlePostDto.email)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{articleId}")
    fun updateArticle(
        @PathVariable @Positive articleId: Long,
        @Valid @RequestBody articlePatchDto: ArticleDto.Input
    ): ResponseEntity<ArticleDto.Response> {
        val article = articleService.updateArticle(
            articleId, articlePatchDto.email, articlePatchDto.password,
            articleMapper.articleInputDtoToArticle(articlePatchDto)
        )
        val response = articleMapper.articleToArticleResponse(article, articlePatchDto.email)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{articleId}")
    fun deleteArticle(
        @PathVariable @Positive articleId: Long,
        @Valid @RequestBody articleDeleteDto: BaseUserDto
    ): ResponseEntity<Void> {
        articleService.deleteArticle(articleId, articleDeleteDto.email, articleDeleteDto.password)
        return ResponseEntity.ok().build()
    }
}
