package com.wanted.backend.dto.response;

import com.wanted.backend.domain.Board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FetchBoardByIdResDto {
    private Long boardId;
    private String title;
    private String content;
    private Long userId;

    @Builder
    public FetchBoardByIdResDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.userId = board.getUser().getId();
    }

}
