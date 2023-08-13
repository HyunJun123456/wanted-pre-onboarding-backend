package com.wanted.backend.dto.request;

import lombok.Getter;

@Getter
public class EditBoardByIdReqDto {
    private Long boardId;
    private String title;
    private String content;
}
