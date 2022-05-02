package com;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.api.entity.Board;
import com.api.entity.User;
import com.api.entity.dto.BoardDto;
import com.api.service.BoardService;
import com.oauth.repository.UserRepository;

@SpringBootTest
@Transactional
public class BoardApplicationTests {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Rollback(false)
	public void saveBoard() {
		Long id = Long.parseLong("27");
		User user = userRepository.getById(id);
		Board board = new Board(null, "테스트입니다3", "안녕하세요.", 0, user);
		
		BoardDto boardDto = new BoardDto(board);
		
		Long resultId = boardService.SaveBoard(boardDto);
		
		System.out.println(resultId);
	}
	
	@Test
	public void findBoard() {
		List<BoardDto> list = boardService.findAll();
		
		for(BoardDto board : list) {
			System.out.println(board.getSubject());
		}
	}
}
