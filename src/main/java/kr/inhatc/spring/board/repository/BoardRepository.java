package kr.inhatc.spring.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.board.entity.Boards;

@Repository
public interface BoardRepository extends CrudRepository<Boards, Integer> {

	// 게시글 불러오기
	List<Boards> findAllByOrderByBoardIdxDesc();

	// 검색 - 사용자 이름 혹은 제목으로
	Page<Boards> findByUsernameContainingOrTitleContaining(String searchText, String searchText2, Pageable pageable);
}
