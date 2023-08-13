package com.wanted.backend.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBoardReqDto {
    private String title;
    private String content;

}
