package com.shoppingCart.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false, length = 100)
	private String createdBy;

	@Column(name = "updated_by", length = 100)
	private String updatedBy;

	@Version
	@Column(name = "version", nullable = false, columnDefinition = "bigint default 0")
	private Long version;

	@Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
	private boolean deleted = false;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@PrePersist
	protected void onCreate() {
		updatedAt = null;
		updatedBy = null;
	}

	@PreUpdate
	protected void onUpdate() {
	    updatedAt = LocalDateTime.now();
	    updatedBy = "system";
	}
	
	public void softDelete() {
	    this.deleted = true;
	    this.deletedAt = LocalDateTime.now();
	}
}