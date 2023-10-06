package com.efub.community.domain.board.domain.builder;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.member.domain.Member;

public class BoardBuilder {
    public static Board build(Member owner){
        final String VALID_NAME = "board_name";
        final String VALID_DESCRIPTION = "this is board";
        final Member VALID_OWNER = owner;
        return Board.builder()
                .name(VALID_NAME)
                .description(VALID_DESCRIPTION)
                .owner(VALID_OWNER)
                .build();

    }
}
