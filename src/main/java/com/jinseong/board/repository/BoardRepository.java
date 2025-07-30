package com.jinseong.board.repository;

import com.jinseong.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 조회수 증가 -> update board_table set board_hits=board_hits+1 where id=?  -> 기존의 조회수에서 하나 올리는
    // db를 사용해서 조회수 증가 .
    // Query 어노테이션 사용
    @Modifying
    // delete 이런 쿼리가 필요할 땐 Modifying 추가
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

}
