package yourssu.yourssuAssigmnet.domain.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null
    // 엔티티 생성 시간 저장

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
    // 엔티티 수정 시간 저장
}
