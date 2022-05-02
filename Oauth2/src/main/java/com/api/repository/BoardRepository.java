package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
