package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.Comment;
import com.efub.community.domain.board.domain.Post;
import com.efub.community.domain.board.domain.builder.BoardBuilder;
import com.efub.community.domain.board.domain.builder.CommentBuilder;
import com.efub.community.domain.board.domain.builder.PostBuilder;
import com.efub.community.domain.member.domain.Member;
import com.efub.community.domain.member.domain.MemberBuilder;
import com.efub.community.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
class CommentRepositoryTest {

    private Member savedValidWriter;
    private Board savedValidBoard;

    private Post savedValidPost;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void setUp() {
        final Member writer = MemberBuilder.build();
        final Board board = BoardBuilder.build(writer);
        final Post post = PostBuilder.build(writer, board);
        savedValidWriter=  memberRepository.save(writer);
        savedValidBoard = boardRepository.save(board);
        savedValidPost = postRepository.save(post);

        final Comment firstComment = CommentBuilder.builder(savedValidWriter, savedValidPost);
        final Comment secondComment = CommentBuilder.builder(savedValidWriter, savedValidPost);

        commentRepository.save(firstComment);
        commentRepository.save(secondComment);
    }

    @AfterEach//JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드, 다음 테스트에 H2에 남아있는 데이터가 영향을 주지 않기 위해 삭제
    public void cleanUp()
    {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("작성자로_댓글_조회_성공")
    void findByWriter_ShouldReturnComments_WhenGivenValidMember() {
        //when
        final List<Comment> comments = commentRepository.findByWriter(savedValidWriter);

        //then
        assertEquals(2, comments.size());
    }

    @Test
    @DisplayName("게시글로_댓글_조회_성공")
    void findByPost_ShouldReturnComments_WhenGivenValidPost() {
        //when
        final List<Comment> comments = commentRepository.findByPost(savedValidPost);

        //then
        assertEquals(2, comments.size());
    }

    @Test
    @DisplayName("잘못된_작성자로_댓글_조회_결과_없음")
    void findByWriter_ShouldReturnEmptyList_WhenGivenInvalidMember() {
        final Member invalidMember = MemberBuilder.build(3L);

        //when
        final List<Comment> comments = commentRepository.findByWriter(invalidMember);

        //then
        assertTrue(comments.isEmpty());
    }

    @Test
    @DisplayName("잘못된_게시글로_댓글_조회_결과_없음")
    void findByPost_ShouldReturnEmptyList_WhenGivenInvalidPost() {
        final Post invalidPost = PostBuilder.build(savedValidWriter, savedValidBoard);//댓글이 없는 경우
        postRepository.save(invalidPost);
        //when
        final List<Comment> comments = commentRepository.findByPost(invalidPost);

        //then
        assertTrue(comments.isEmpty());
    }


}