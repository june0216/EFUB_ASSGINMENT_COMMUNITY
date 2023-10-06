package com.efub.community.domain.board.repository;

import com.efub.community.domain.board.domain.Board;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
class BoardRepositoryTest {

    private Member savedValidWriter;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @AfterEach//JUnit에서 단위 테스트가 끝날때마다 수행되는 메서드, 다음 테스트에 H2에 남아있는 데이터가 영향을 주지 않기 위해 삭제
    public void cleanUp()
    {
        boardRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @BeforeEach
    public void setUp(){
        final Member writer = MemberBuilder.build();
        savedValidWriter=  memberRepository.save(writer);
    }


    @Test
    @DisplayName("게시판_생성_성공")
    void findAllByOrderByBoardIdDesc_GivenValidInfo_ShouldSetAttributesCorrectly() {
        final String VALID_NAME = "board_name";
        final String VALID_DESCRIPTION = "this is board";
        final Board board = Board.builder()
                .name(VALID_NAME)
                .description(VALID_DESCRIPTION)
                .owner(savedValidWriter)
                .build();

        //when
        final Board savedBoard = boardRepository.save(board);


        //then
        assertThat(savedBoard.getName()).isEqualTo(VALID_NAME);
        assertThat(savedBoard.getDescription()).isEqualTo(VALID_DESCRIPTION);
        assertThat(savedBoard.getOwner().getMemberId()).isEqualTo(savedValidWriter.getMemberId());

    }

    @Test
    @DisplayName("전체_게시판_내림차순으로_조회_성공")
    void findAllByOrderByBoardIdDesc_SuccessTest() {
        // 테스트 데이터 생성
        final Member owner = MemberBuilder.build();
        final String VALID_NAME_1 = "board1";
        final Board firstBoard = createDefaultBoard(VALID_NAME_1, owner);
        final String VALID_NAME_2 = "board2";
        final Board secondBoard = createDefaultBoard(VALID_NAME_2, owner);

        //when
        final List<Board> boards = boardRepository.findAllByOrderByBoardIdDesc();

        // then
        assertFalse(boards.isEmpty(), "게시판 리스트가 비어있습니다.");
        assertEquals(secondBoard.getBoardId(), boards.get(0).getBoardId(), "첫 번째 게시판의 ID가 예상과 다릅니다.");
        assertEquals(firstBoard.getBoardId(), boards.get(1).getBoardId(), "두 번째 게시판의 ID가 예상과 다릅니다.");
    }

    @Test
    @DisplayName("게시판_이름_존재여부_실패")
    void existsByName_FailureTest() {
        final String nonExistentName = "nonExistentName";  // 데이터베이스에 저장되지 않은 이름

        //when
        final boolean exists = boardRepository.existsByName(nonExistentName);

        //then
        assertFalse(exists, "이름이 데이터베이스에 없음에도 불구하고 true를 반환하였습니다.");
    }

    @Test
    @DisplayName("게시판_조회_실패")
    void findById_FailureTest() {
        final Long nonExistentBoardId = 9999L;  // 데이터베이스에 저장되지 않은 ID

        //when
        final Optional<Board> foundBoard = boardRepository.findById(nonExistentBoardId);

        //then
        assertFalse(foundBoard.isPresent(), "ID로 게시판을 찾지 못했는데도 Optional이 empty가 아닙니다.");
    }


    private Board createDefaultBoard(String name, Member member){
        final String VALID_NAME = name;
        final String VALID_DESCRIPTION = "this is board";
        final Member VALID_OWNER = member;
        final Board board =  Board.builder()
                .name(VALID_NAME)
                .description(VALID_DESCRIPTION)
                .owner(VALID_OWNER)
                .build();
        return board;
    }


}