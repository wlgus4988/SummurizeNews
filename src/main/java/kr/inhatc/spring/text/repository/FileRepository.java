package kr.inhatc.spring.text.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.text.entity.FileDto;


@Repository
public interface FileRepository extends JpaRepository<FileDto, String>{

	List<FileDto> findAllByUsername(String username);

	FileDto findByUsername(String username);

	void deleteByUsername(String username);

	List<FileDto> findAllByOrderByUsernameDesc();

}
