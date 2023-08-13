package com.wanted.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.wanted.backend.config.auth.LoginUser;
import com.wanted.backend.domain.Board;
import com.wanted.backend.dto.request.AddBoardReqDto;
import com.wanted.backend.dto.request.EditBoardByIdReqDto;
import com.wanted.backend.dto.response.FetchBoardByIdResDto;
import com.wanted.backend.dto.response.FetchBoardsResDto;
import com.wanted.backend.handler.ex.CustomApiException;
import com.wanted.backend.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void addBoard(LoginUser loginUser, AddBoardReqDto addBoardReqDto) {
        Board board = Board.builder()
                .loginUser(loginUser)
                .addBoardReqDto(addBoardReqDto)
                .build();
        boardRepository.save(board);
    }

    public Page<FetchBoardsResDto> fetchBoards(Pageable pageable) {
        Page<Board> boardsPS = boardRepository.findAll(pageable);
        return boardsPS.map(FetchBoardsResDto::new);
    }

    public FetchBoardByIdResDto fetchBoardById(Long boarId) {
        Board boardPS = boardRepository.findByUserId(boarId)
                .orElseThrow(() -> new CustomApiException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        FetchBoardByIdResDto fetchBoardByIdResDto = FetchBoardByIdResDto.builder().board(boardPS).build();
        return fetchBoardByIdResDto;
    }

    public void editBoardById(LoginUser loginUser, EditBoardByIdReqDto editBoardByIdReqDto) {
        Long boardId = editBoardByIdReqDto.getBoardId();
        Board boardPS = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomApiException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        // 게시글 작성자와 로그인한 유저의 id가 같은경우 수정 가능
        if (boardPS.getUser().getId().equals(loginUser.getUser().getId())) {
            if (editBoardByIdReqDto.getTitle() != null) {
                boardPS.setTitle(editBoardByIdReqDto.getTitle());
            }
            if (editBoardByIdReqDto.getContent() != null) {
                boardPS.setContent(editBoardByIdReqDto.getContent());
            }
            boardRepository.save(boardPS);
        } else {
            throw new CustomApiException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

    public void removeBoard(LoginUser loginUser, Long boardId) {
        Board boardPS = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomApiException("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        if (boardPS.getUser().getId().equals(loginUser.getUser().getId())) {
            boardRepository.delete(boardPS);
        } else {
            throw new CustomApiException("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

}
