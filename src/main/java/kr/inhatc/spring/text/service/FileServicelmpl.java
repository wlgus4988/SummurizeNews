package kr.inhatc.spring.text.service;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.text.entity.FileDto;
import kr.inhatc.spring.text.repository.FileRepository;
import kr.inhatc.spring.user.repository.UserRepository;


@Service
public class FileServicelmpl implements FileService{

	@Autowired
	UserRepository userRespository;
	
	@Autowired
	FileRepository fileRepository;

	@Override
	public FileDto fileDetail(String username) {
		
		FileDto optional = fileRepository.findByUsername(username);
		
		return optional;
	}

	@Override
	@Transactional
	public void fileDelete(FileDto file) {
		
		fileRepository.deleteByUsername(file.getUsername());
		
		File delFile = new File("src/main/resources/static" + file.getStoredFilePath());
		delFile.delete();
		
	}

	@Override
	public List<FileDto> fileList() {
		
		List<FileDto> list =  fileRepository.findAllByOrderByUsernameDesc();
		
		return list;
	}	
}


