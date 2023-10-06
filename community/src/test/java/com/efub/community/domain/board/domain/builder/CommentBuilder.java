package com.efub.community.domain.board.domain.builder;

import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.member.domain.Member;

public class CommentBuilder {
    public static Comment builder(Member writer, Post post){
        final String content = "HELLO";
        return Comment.builder()
                .content(content)
                .post(post)
                .writer(writer)
                .anonymous(true)
                .build();

    }
}
