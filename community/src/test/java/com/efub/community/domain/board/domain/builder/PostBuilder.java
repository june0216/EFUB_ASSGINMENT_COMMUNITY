package com.efub.community.domain.board.domain.builder;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;

public class PostBuilder {
    public static Post build(){
        final Member writer = MemberBuilder.build();
        final Board board = BoardBuilder.build(writer);
        final String VALID_CONTENT = "content";
        return Post.builder()
                .content(VALID_CONTENT)
                .writer(writer)
                .board(board)
                .anonymous(true)
                .build();
    }
    public static Post build(Member writer, Board board){
        final String VALID_CONTENT = "content";
        return Post.builder()
                .content(VALID_CONTENT)
                .writer(writer)
                .board(board)
                .anonymous(true)
                .build();
    }

    public static Post build(String content, Member writer, Board board){
        return Post.builder()
                .content(content)
                .writer(writer)
                .board(board)
                .anonymous(true)
                .build();
    }
}
