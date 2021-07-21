package kr.inhatc.spring.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.login.security.SecurityUser;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public List<Boards> boardList() {
		
		List<Boards> list = boardRepository.findAllByOrderByBoardIdxDesc();
		
		return list;
	}

	@Override
	public void saveBoards(Boards board, int hitCnt) {
		
		SecurityUser user = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		board.setUsername(user.getUsername());
		board.setHitCnt(hitCnt);

		boardRepository.save(board);
	}
	
	@Override
	public Boards boardDetail(int boardIdx) {

		Optional<Boards> optional = boardRepository.findById(boardIdx);
		
		if(optional.isPresent()) { // id가 존재한다면
			Boards board = optional.get();
			
			board.setHitCnt(board.getHitCnt()+1);
			
			boardRepository.save(board);
			
			return board;
			
		} else { // 없을때는
			
			throw new NullPointerException();
			
		}
	}

	@Override
	public void boardDelete(int boardIdx) {

		boardRepository.deleteById(boardIdx);
		
	}
	
	@Override
	public Page<Boards> boardjpaPageList(String searchText, Pageable pageable) {
		
		Page<Boards> list = boardRepository.findByUsernameContainingOrTitleContaining(searchText, searchText, pageable);

		return list;
	}
}