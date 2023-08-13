package com.wanted.backend.dto.response;

import com.wanted.backend.domain.Board;

import lombok.Getter;

@Getter
public class FetchBoardsResDto {
    private Long boardId;
    private String title;
    private String content;
    private Long userId;

    public FetchBoardsResDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getId();
    }

}
