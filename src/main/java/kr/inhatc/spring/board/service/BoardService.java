package kr.inhatc.spring.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.board.entity.Boards;

public interface BoardService {

	List<Boards> boardList();
	
	void saveBoards(Boards board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt);
	
	Boards boardDetail(int boardIdx);
	
	void boardDelete(int boardIdx);

	Files selectFileInfo(int idx, int boardIdx);


}
