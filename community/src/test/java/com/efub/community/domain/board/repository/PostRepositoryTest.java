package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Board;
import com.efub.community.domain.board.domain.builder.BoardBuilder;
import com.efub.community.domain.board.domain.Post;
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
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
class PostRepositoryTest {
    private Member savedValidWriter;
    private Board savedValidBoard;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach//JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드, 다음 테스트에 H2에 남아있는 데이터가 영향을 주지 않기 위해 삭제
    public void cleanUp()
    {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
        postRepository.deleteAll();
    }

    @BeforeEach
    public void setUp(){
        final Member writer = MemberBuilder.build();
        final Board board = BoardBuilder.build(writer);
        savedValidWriter=  memberRepository.save(writer);
        savedValidBoard = boardRepository.save(board);
    }

    @Test
    @DisplayName("작성자_게시글_조회_성공")
    void findByWriter_GivenValidWriter_ShouldSuccess() {
        // Given
        final Post firstPost = createDefaultPost(savedValidWriter, savedValidBoard);
        final Post secondPost = createDefaultPost(savedValidWriter, savedValidBoard);

        // When
        final List<Post> posts = postRepository.findByWriter(savedValidWriter);

        // Then
        assertThat(posts).hasSize(2);
    }

    @Test
    @DisplayName("작성자_게시글_조회_실패_다른작성자")
    void findByWriter_GivenInvalidWriter_ShouldNotBeDifferent() {
        // Given
        final Post firstPost = createDefaultPost(savedValidWriter, savedValidBoard);
        final Post secondPost = createDefaultPost(savedValidWriter, savedValidBoard);

        // When
        final List<Post> posts = postRepository.findByWriter(savedValidWriter);

        // Then
        assertThat(posts).hasSize(2);
    }

    @Test
    @DisplayName("게시글_조회_내림차순_성공")
    void findAllByOrderByPostIdDesc_ShouldSuccess() {
        // Given
        final Post firstPost = createDefaultPost(savedValidWriter, savedValidBoard);
        final Post secondPost = createDefaultPost(savedValidWriter, savedValidBoard);

        // When
        final List<Post> posts = postRepository.findAllByOrderByPostIdDesc();

        // Then
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).getPostId()).isGreaterThan(posts.get(1).getPostId());
    }



    @Test
    @DisplayName("게시글_생성_실패_null")
    void savePost_GivenInvalidConetent_ShouldNotBeNull() {
        // Given
        final String INVALID_CONTENT = null;
        final boolean VALID_ANONYMOUS = true;
        final Post post = Post.builder()
                .content(INVALID_CONTENT)
                .anonymous(VALID_ANONYMOUS)
                .writer(savedValidWriter)
                .board(savedValidBoard)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            postRepository.save(post);
            postRepository.flush(); // 즉시 쿼리 실행
        });

        testEntityManager.clear(); // 여기서 영속성 컨텍스트 초기화
    }


    private Post createDefaultPost(Member writer, Board board){
        final String VALID_CONTENT = "content";
        Post post =  Post.builder()
                .content(VALID_CONTENT)
                .writer(writer)
                .board(board)
                .anonymous(true)
                .build();
        return postRepository.save(post);
    }



}