package com.jinseong.board.service;

import com.jinseong.board.dto.BoardDTO;
import com.jinseong.board.entity.BoardEntity;
import com.jinseong.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// 서비스 클래스에서
// DTO -> Entity 변환  ( Entity 클래스에서 )
// Entity -> DTO 변환  ( DTO 클래스에서 )
@Service
@RequiredArgsConstructor // 생성자? 의존성 주입
public class BoardService  {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        //findall -> 리포지토리에서 가지고오면 엔티티 형태로 넘어오게 됨. entity를 dto 객체로 옮겨 담아 리턴해야함
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    // jpa가 제공해주는 메서드는, find 등등, 메서드 규칙을 따라가면 자동으로 쿼리가 만들어지는 형태들이 굉장히 많음
    // 조회수 증가라던가 , 특수한 목적을 가진 쿼리들은 제한됨. 직접 별도의 메서드를 정의해야할 필용 있음.
    @Transactional
    // transactional -> 붙이지 않으면 에러 -> 스프링 jpa 트랜잭션 모두 하는데  ,수동적으로 수행해야하는 쿼리는
    // transactional 을 사용해야한다 ? ->
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }


}
