package kr.inhatc.spring.board.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.thymeleaf.standard.expression.Each;


import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;
import kr.inhatc.spring.login.security.SecurityUser;
import kr.inhatc.spring.login.service.SecurityUserDetailService;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	private FileUtils fileUtils;

	@Override
	public List<Boards> boardList() {
		List<Boards> list = boardRepository.findAllByOrderByBoardIdxDesc();
		return list;
	}

	@Override
	public void saveBoards(Boards board, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) {
		
		SecurityUser user = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//System.out.println("username ==============>" + user.getUsername());

		board.setUsername(user.getUsername());
		board.setHitCnt(hitCnt);
		
		
		List<Files> list = fileUtils.parseFileInfo( multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list)==false){
			board.setFileList(list);
		}

		boardRepository.save(board);
	}
	
//	@Override
//	public void boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
//		boardMapper.boardInsert(board);
//
//		// 파일임시확인
//		// multi~가 비지않았다면
////		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) { 
////			Iterator<String> iter = multipartHttpServletRequest.getFileNames();
////			
////			// 다음내용 가져오기
////			while(iter.hasNext()) {
////				String name = iter.next();
////				
////				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
////				for (MultipartFile multipartFile : list) {
////					System.out.println("==========================> file name : " + multipartFile.getOriginalFilename());
////					System.out.println("==========================> file size : " + multipartFile.getSize());
////					System.out.println("==========================> file type : " + multipartFile.getContentType());
////					
////				}
////			}
////		}

//		// 1. 파일 저장
//		List<FileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
//
//		// 2.디비 저장
//
//		if (CollectionUtils.isEmpty(list) == false) {
//			System.out.println("============================================================================> 디비저장하러 감");
//			boardMapper.boardFileInsert(list);
//		}
//	}

	@Override
	public Boards boardDetail(int boardIdx) {
//		BoardDto board = boardMapper.boardDetail(boardIdx);
//
//		// 파일 정보
//		List<FileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
//		board.setFileList(fileList);
//
//		// 보드상세페이지 띄운후 히트수를 올릴건지 그 코드 순서에 따라 제어가능 / 조회수
//		boardMapper.updateHit(boardIdx); // 히트수 업데이트
//		return board;
		Optional<Boards> optional = boardRepository.findById(boardIdx);
		if(optional.isPresent()) {// id가 존재한다면
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
		//boardMapper.boardDelete(boardIdx);
		boardRepository.deleteById(boardIdx);
	}

//	@Override
//	public void boardFileDelete(int idx, int boardIdx) {
//		boardRepository.deleteBoardFile(idx, boardIdx);
//	}

	@Override
	public Files selectFileInfo(int idx, int boardIdx) {
		Files boardFile = boardRepository.selectFileInfo(idx, boardIdx);
		return boardFile;
	}

}