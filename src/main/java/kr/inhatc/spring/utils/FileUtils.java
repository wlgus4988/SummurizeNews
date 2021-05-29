package kr.inhatc.spring.utils;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.entity.Files;
import kr.inhatc.spring.user.entity.Users;

@Component
public class FileUtils {

	Users user;
	
	public List<Files> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest){
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) { // 아무런 파일이 없을 때
			return null;
		}
		
		List<Files> fileList = new ArrayList<Files>();
		
		// 파일이 업로드 될 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); // 만드는 날짜
		ZonedDateTime current = ZonedDateTime.now(); // 현재시간 가져옴
		String path = "images/" + current.format(format); // 경로
		
		File file = new File(path);
		if(file.exists() == false) { // 파일이 존재하지 않는다면
			file.mkdirs(); // 파일을 만든다
		}
		
		// 파일 정보 가져오기
		Iterator<String> iter = multipartHttpServletRequest.getFileNames();
		
		String originalFileExtension = null;
		while(iter.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());
			
			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) { //파일이 비지 않았다면
					
					String contentType = multipartFile.getContentType(); // 컨텐츠 타입 확인
					
					// 확장자가 없다면
					if(ObjectUtils.isEmpty(contentType)) { 
						break;
						
					} else { // 확장자 생성
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					
					// 새 파일 이름 만들기(나노초 단위로)
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					Files boardFile = new Files();
					//boardFile.setUsername("admin"); 
					//boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize()); // 파일 사이즈
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename()); 
					boardFile.setStoredFilePath(path + "/" + newFileName); // 파일 위치
					fileList.add(boardFile); // 파일리스트에 추가
					
					// 파일 예외 처리
					file = new File(path + "/" + newFileName);
					try { 
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) { // 예외처리
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return fileList;
	}
	
	
}
