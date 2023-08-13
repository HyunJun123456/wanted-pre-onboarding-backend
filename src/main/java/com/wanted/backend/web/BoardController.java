package com.wanted.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.backend.config.auth.LoginUser;
import com.wanted.backend.dto.request.AddBoardReqDto;
import com.wanted.backend.dto.request.EditBoardByIdReqDto;
import com.wanted.backend.dto.response.FetchBoardByIdResDto;
import com.wanted.backend.dto.response.FetchBoardsResDto;
import com.wanted.backend.dto.response.ResponseDto;
import com.wanted.backend.service.BoardService;

@RequestMapping("/api")
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/s/add/board")
    public ResponseEntity<?> addBoard(@RequestBody AddBoardReqDto AddBoardReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        boardService.addBoard(loginUser, AddBoardReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 생성 완료", null), HttpStatus.CREATED);
    }

    // Board 객체를 넘겨주는 것이 아닌 DTO로 응답 예정
    @GetMapping("/fetch/boards")
    public ResponseEntity<?> fetchBoards(Pageable pageable) {
        Page<FetchBoardsResDto> fetchBoardsResDto = boardService.fetchBoards(pageable);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시글 목록 조회 완료", fetchBoardsResDto), HttpStatus.OK);
    }

    @GetMapping("/fetch/board/{boardId}")
    public ResponseEntity<?> fetchBoardById(@PathVariable Long boardId) {
        FetchBoardByIdResDto fetchBoardByIdResDto = boardService.fetchBoardById(boardId);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 게시글 조회 완료", fetchBoardByIdResDto), HttpStatus.OK);
    }

    @PatchMapping("/s/edit/board")
    public ResponseEntity<?> editBoardById(@RequestBody EditBoardByIdReqDto editBoardByIdReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        boardService.editBoardById(loginUser, editBoardByIdReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 게시글 수정 완료", null), HttpStatus.OK);
    }

    @DeleteMapping("/s/edit/board/{boardId}")
    public ResponseEntity<?> removeBoard(@PathVariable Long boardId,
            @AuthenticationPrincipal LoginUser loginUser) {
        boardService.removeBoard(loginUser, boardId);
        return new ResponseEntity<>(new ResponseDto<>(1, "특정 게시글 삭제 완료", null), HttpStatus.OK);
    }

}
