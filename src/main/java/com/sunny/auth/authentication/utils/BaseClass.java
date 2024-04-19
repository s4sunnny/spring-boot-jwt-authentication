package com.sunny.auth.authentication.utils;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseClass {

	@CreatedDate
	@CreationTimestamp
	private LocalDateTime createdDate;

	@CreatedBy
	@Column(length = 50)
	private String createdBy;

	@LastModifiedDate
	private LocalDateTime updatedDate;

	@LastModifiedBy
	@Column(length = 50)
	private String updatedBy;

//	@PreUpdate
//	@PrePersist
//	public void beforeAnyUpdate() {
//	}
}
