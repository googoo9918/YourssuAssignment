package yourssu.yourssuAssigmnet.domain.article.repository

import org.springframework.data.jpa.repository.JpaRepository
import yourssu.yourssuAssigmnet.domain.article.entity.Article

interface ArticleRepository : JpaRepository<Article, Long>
