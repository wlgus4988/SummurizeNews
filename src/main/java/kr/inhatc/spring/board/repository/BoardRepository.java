package kr.inhatc.spring.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;

@Repository
public interface BoardRepository extends CrudRepository<Boards, Integer> {

	List<Boards> findAllByOrderByBoardIdxDesc();
	
	@Query("SELECT file FROM Files file WHERE board_idx = :boardIdx AND idx = :idx")
	Files selectFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx);

	Page<Boards> findByContentsContainingOrTitleContaining(String searchText, String searchText2, Pageable pageable);

//	Page<Boards> findByPage(Pageable pageable);

}
