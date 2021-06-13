package kr.inhatc.spring.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.user.entity.FileDto;
import kr.inhatc.spring.user.entity.Users;

@Repository
public interface FileRepository extends JpaRepository<FileDto, String>{

//	List<FileDto> findByUserid(String id);

	List<FileDto> findAllByUsername(String username);

	FileDto findByUsername(String username);

	void deleteByUsername(String username);

	List<FileDto> findAllByOrderByUsernameDesc();

//	FileDto deleteByIdx(int idx);

//	List<Users> findAllByOrderByIdDesc();

}
