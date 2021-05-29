package kr.inhatc.spring.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Boards;
import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.board.repository.BoardRepository;
import kr.inhatc.spring.board.service.BoardService;
import kr.inhatc.spring.user.repository.UserRepository;

//@RestController 결과물을 바로 받아옴
@Controller // html 파일로 넘겨줌
public class BoardController {

	// log로 불러온 정보를 파일로 저장하기 위해서
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardRepository boardRepository;



	// 글쓰기
	@RequestMapping("/test/mainview")
	public String testPage() {
		// 뷰어 이동
		return "test/mainview";
	}

	@RequestMapping(value = "/board/boardList/admin", method = RequestMethod.GET)
	public String boardList_admin(Model model,
			@PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		Page<Boards> list = boardRepository.findByContentsContainingOrTitleContaining(searchText, searchText, pageable);

		int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
		int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("list", list);

		// log.debug("=========================>" + "여기 !"); // 디버그로 찍는거
		return "Admin/boardList_Admin";
	}

	@RequestMapping(value = "/board/boardList", method = RequestMethod.GET)
	public String boardList(Model model, @PageableDefault(size = 2) org.springframework.data.domain.Pageable pageable,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		// 서비스 로직
		Page<Boards> list = boardRepository.findByContentsContainingOrTitleContaining(searchText, searchText, pageable);

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
	public String boardWrite() {
		// 뷰어 이동
		return "/board/boardWrite";
	}

	// 글쓰기 다음 저장
	@RequestMapping(value = "/board/boardInsert", method = RequestMethod.POST)
	public String boardInsert(Boards board, MultipartHttpServletRequest multipartHttpServletRequest) {
		int hitCnt = board.getHitCnt();

		boardService.saveBoards(board, multipartHttpServletRequest, hitCnt);
		// 게시판으로 다시 이동
		return "redirect:/board/boardList"; // redirect : controller의 boardList를 다시 호풀하는 것
	}

	// 쓴 글 불러오기
	@RequestMapping(value = "/board/boardDetail/{boardIdx}", method = RequestMethod.GET)
	public String boardDetail(@PathVariable("boardIdx") int boardIdx, Model model) {
		// 상세정보 가져오기
		Boards board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		// 게시판으로 다시 이동
		return "board/boardDetail";
	}

	// 수정하고 업데이트
	@RequestMapping(value = "/board/boardUpdate/{boardIdx}", method = RequestMethod.POST)
	public String boardUpdate(@PathVariable("boardIdx") int boardIdx, Boards board) {

		int hitCnt = board.getHitCnt();

		board.setBoardIdx(boardIdx);

		boardService.saveBoards(board, null, hitCnt + 1);
		// 게시판으로 다시 이동
		return "redirect:/board/boardList";
	}

	// 삭제하기
	// @GetMapping @PostMapping @DeleteMapping @PutMapping
	@RequestMapping(value = "/board/boardDelete/{boardIdx}", method = RequestMethod.GET)
	// RquestParam => (매개변수에서 넘어온 이름) 외부(웹?)에서 사용 int => (지역변수)내부에서 사용
	public String boardDelete(@PathVariable("boardIdx") int boardIdx) {
		boardService.boardDelete(boardIdx);
		// 게시판으로 다시 이동
		return "redirect:/board/boardList";
	}

	// 파일 다운로드
	@RequestMapping(value = "/board/downloadBoardFile", method = RequestMethod.GET)
	public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception {

		Files boardFile = boardService.selectFileInfo(idx, boardIdx);
		// System.out.println("===============================>"+idx);
		// System.out.println("===============================>"+boardIdx);

		if (ObjectUtils.isEmpty(boardFile) == false) { // 파일이 있다면
			String fileName = boardFile.getOriginalFileName();

			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

			// response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length); // 길이
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");

			response.getOutputStream().write(files); // 버퍼 출력
			response.getOutputStream().flush(); // 버퍼에 있는거 밀어내기
			response.getOutputStream().close();

			System.out.println("=====================> idx :" + idx);
			System.out.println("=====================> boardIdx :" + boardIdx);
		}
	}

}
