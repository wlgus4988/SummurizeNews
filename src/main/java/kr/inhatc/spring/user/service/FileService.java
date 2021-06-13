package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.user.entity.FileDto;
import kr.inhatc.spring.user.entity.Users;

public interface FileService {

	FileDto fileDetail(String username);

//	FileDto fileList(List<Users> list);

//	void fileDelete(String id);

	void fileDelete(FileDto file);

	List<FileDto> fileList();


}
