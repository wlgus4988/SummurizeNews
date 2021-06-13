package kr.inhatc.spring.user.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.el.stream.Optional;

import kr.inhatc.spring.user.entity.FileDto;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.FileRepository;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtils;


@Service
public class FileServicelmpl implements FileService{

	@Autowired
	UserRepository userRespository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private FileUtils fileUtils;
	

	
//	@Autowired
//	FileDto filedto;

	@Override
	public FileDto fileDetail(String username) {
		//java.util.Optional<Users> optional = userRespository.findById(id);
		FileDto optional = fileRepository.findByUsername(username);
		System.out.println("hoxi??? : "+ optional);
		
		return optional;
		
	}

//	@Override
//	public FileDto fileList(List<Users> list) {
//		ArrayList pitches = new ArrayList();
//		
////		System.out.println("ehal~~~ : " + list.get(0));
//		for(int i = 0; i<list.size(); i++) {
//			pitches.add(list.get(i).getId());
//		}
//		System.out.println("asdf : " + pitches);
//		
////		FileDto file = 
//		
//		return null;
//	}

//	@Override
//	@Transactional
//	public void fileDelete(String id) {
//		fileRepository.deleteByUserid(id);
//		
//	}

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
		System.out.println("lst~~~~!!! : " + list);
		
		return list;
	}

	

}


