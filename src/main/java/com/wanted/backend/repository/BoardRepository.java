package com.wanted.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.backend.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByUserId(Long userId);

}
