package kr.inhatc.spring.board.entity;

import java.util.List;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 데이블을 만드는 엔티티 객체 생성
@Table(name = "PRO_BOARD")
@NoArgsConstructor // 디폴트 생성자
@AllArgsConstructor // 전체 컬럼 생성자
@Data // get, set
@Builder // 객체 생성 도와주는 도구
public class Boards {

	@Id
	@Column(name = "BOARD_IDX")
	// Auto 시퀀스 방식
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int boardIdx;
	
	private String title;
	private String contents;
	private int hitCnt;
	private String username;

	@Temporal(TemporalType.DATE)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDatetime;


}
