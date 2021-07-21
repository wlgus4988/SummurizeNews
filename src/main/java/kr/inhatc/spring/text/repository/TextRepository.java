package kr.inhatc.spring.text.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.text.entity.Texts;


@Repository
public interface TextRepository extends CrudRepository<Texts, Integer> {

	List<Texts> findAllByOrderByTextIdxDesc();

}
