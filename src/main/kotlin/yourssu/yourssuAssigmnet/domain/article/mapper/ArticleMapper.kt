package yourssu.yourssuAssigmnet.domain.article.mapper

import org.mapstruct.Mapper
import yourssu.yourssuAssigmnet.domain.article.dto.ArticleDto
import yourssu.yourssuAssigmnet.domain.article.entity.Article

@Mapper(componentModel = "spring")
interface ArticleMapper {
    fun articleInputDtoToArticle(articleInputDto: ArticleDto.Input): Article
    fun articleToArticleResponse(article: Article, email: String): ArticleDto.Response
}
