package com.api.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.entity.Board;
import com.api.entity.dto.BoardDto;
import com.api.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;

	public List<BoardDto> findAll() {
		return boardRepository.findAll().stream().map(content -> new BoardDto(content)).collect(Collectors.toList());
	}
	
	@Transactional
	public Long SaveBoard(BoardDto boardDto) {
		Board board = boardDto.toEntity();
		boardRepository.save(board);
		return board.getId();
	}
}
