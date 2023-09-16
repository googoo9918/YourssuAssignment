package yourssu.yourssuAssigmnet.domain.article.repository

import yourssu.yourssuAssigmnet.domain.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long>