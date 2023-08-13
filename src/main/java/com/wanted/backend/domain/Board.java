package com.wanted.backend.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.wanted.backend.config.auth.LoginUser;
import com.wanted.backend.dto.request.AddBoardReqDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Board(LoginUser loginUser, AddBoardReqDto addBoardReqDto) {
        this.title = addBoardReqDto.getTitle();
        this.content = addBoardReqDto.getContent();
        this.user = loginUser.getUser();
    }

}
