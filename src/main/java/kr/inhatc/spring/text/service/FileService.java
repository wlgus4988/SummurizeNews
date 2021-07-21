package kr.inhatc.spring.text.service;

import java.util.List;

import kr.inhatc.spring.text.entity.FileDto;


public interface FileService {

	FileDto fileDetail(String username);

	void fileDelete(FileDto file);

	List<FileDto> fileList();


}
