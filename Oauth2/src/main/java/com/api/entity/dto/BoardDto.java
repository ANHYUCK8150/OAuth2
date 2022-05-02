package com.api.entity.dto;

import com.api.entity.Board;
import com.api.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardDto {
	private Long id;
	private String subject;
	private String context;
	private Integer cnt;
	private User user;
	
	@Builder
	public BoardDto(Board board) {
		this.id = board.getId();
		this.subject = board.getSubject();
		this.context = board.getContext();
		this.cnt = board.getCnt();
		this.user = board.getUser();
	}
	
	public Board toEntity() {
		return Board.builder()
				.id(id)
				.subject(subject)
				.context(context)
				.cnt(cnt)
				.user(user)
				.build();
	}
}
