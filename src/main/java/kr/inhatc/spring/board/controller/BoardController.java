package kr.inhatc.spring.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.board.service.BoardService;
import kr.inhatc.spring.login.security.SecurityUser;


//@RestController 결과물을 바로 받아옴
@Controller // html 파일로 넘겨줌
public class BoardController {

	// log로 불러온 정보를 파일로 저장하기 위해서
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;
	
	
	
	
	// 사용자 게시글 관리===================================================================================================================

	// 게시글 리스트
	@RequestMapping(value = "/board/boardList", method = RequestMethod.GET)
	public String boardList(Model model, @PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		// 서비스 로직
		Page<Boards> list = boardRepository.findByUsernameContainingOrTitleContaining(searchText, searchText, pageable);
	
		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("list", list);
		
		// 뷰어 이동
		return "board/boardList";
	}

	// 글쓰기
	@RequestMapping(value = "/board/boardInsert", method = RequestMethod.GET)
	public String boardWrite(Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		// 뷰어 이동
		return "/board/boardWrite";
	}
	// 글쓰기 다음 저장
	@RequestMapping(value = "/board/boardInsert", method = RequestMethod.POST)
	public String boardInsert(Boards board, Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		int hitCnt = board.getHitCnt();

		boardService.saveBoards(board, hitCnt);
		
		// 게시판으로 다시 이동
		return "redirect:/board/boardList"; // redirect : controller의 "value = /board/boardList" 를 다시 호출
	}
	
	// 쓴 글 불러오기
	@RequestMapping(value = "/board/boardDetail/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail(@PathVariable("boardIdx") int boardIdx, Model model) {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", user.getUsername());
		
		// 상세정보 가져오기
		Boards board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		
		// 게시판으로 다시 이동
		return "board/boardDetail";
	}
	
	// 수정하고 업데이트
		@RequestMapping(value = "/board/boardUpdate/{boardIdx}", method = RequestMethod.POST)
		public String boardUpdate(@PathVariable("boardIdx") int boardIdx, Boards board, Model model) {
			SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("username", user.getUsername());
			
			int hitCnt = board.getHitCnt();

			board.setBoardIdx(boardIdx);

			boardService.saveBoards(board, hitCnt + 1); // 조회수에 +1
			
			// 게시판으로 다시 이동
			return "redirect:/board/boardList";
		}
		
		// 삭제하기
		// @GetMapping @PostMapping @DeleteMapping @PutMapping
		@RequestMapping(value = "/board/boardDelete/{boardIdx}", method = RequestMethod.GET)
		public String boardDelete(@PathVariable("boardIdx") int boardIdx) {   // RequestParam => (매개변수에서 넘어온 이름) 외부에서 사용   int => (지역변수) 내부에서 사용
			
			boardService.boardDelete(boardIdx);
			
			// 게시판으로 다시 이동
			return "redirect:/board/boardList";
		}
	
		
		
		
	// 관리자 게시글 관리===================================================================================================================
	
	@RequestMapping(value = "/board/boardList/admin", method = RequestMethod.GET)
	public String boardList_admin(Model model,
			@PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		
		// 서비스 로직
		Page<Boards> list = boardRepository.findByUsernameContainingOrTitleContaining(searchText, searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		model.addAttribute("list", list);

		return "Admin/boardList_Admin";
	}
	
	// 글쓰기
	@RequestMapping(value = "/board/boardInsert/admin", method = RequestMethod.GET)
	public String boardWrite_admin() {
		
		// 뷰어 이동
		return "/Admin/boardWrite_Admin";
	}
	// 글쓰기 다음 저장
	@RequestMapping(value = "/board/boardInsert/admin", method = RequestMethod.POST)
	public String boardInsert_admin(Boards board) {
		
		int hitCnt = board.getHitCnt();

		boardService.saveBoards(board, hitCnt);
		
		// 게시판으로 다시 이동
		return "redirect:/board/boardList/admin"; 
	}

	// 쓴 글 불러오기
	@RequestMapping(value = "/board/boardDetail/admin/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail_admin(@PathVariable("boardIdx") int boardIdx, Model model) {
		
		// 상세정보 가져오기
		Boards board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		
		// 게시판으로 다시 이동
		return "/Admin/boardDetail_Admin";
	}

	// 수정하고 업데이트
	@RequestMapping(value = "/board/boardUpdate/admin/{boardIdx}", method = RequestMethod.POST)
	public String boardUpdate_admin(@PathVariable("boardIdx") int boardIdx, Boards board) {

		int hitCnt = board.getHitCnt();

		board.setBoardIdx(boardIdx);

		boardService.saveBoards(board, hitCnt + 1);
		
		// 게시판으로 다시 이동
		return "redirect:/board/boardList/admin";
	}

	// 삭제하기
	@RequestMapping(value = "/board/boardDelete/admin/{boardIdx}", method = RequestMethod.GET)
	public String boardDelete_admin(@PathVariable("boardIdx") int boardIdx) {
		
		boardService.boardDelete(boardIdx);
		
		// 게시판으로 다시 이동
		return "redirect:/board/boardList/admin";
	}
}
