package com.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
	private Long id;
	
	@Column(nullable = false)
	private String subject;
	
	@Column
	private String context;
	
	@Column
	@ColumnDefault("0")
	private Integer cnt;
	
	@ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	@Builder
	public Board(Long id, String subject, String context, Integer cnt, User user) {
		this.id = id;
		this.subject = subject;
		this.context = context;
		this.cnt = cnt;
		this.user = user;
	}
	
}
