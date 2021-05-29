package kr.inhatc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// 첨부파일과 관련된 자동 구성을 사용하지 않도록 설정
// exclude = {MultipartAutoConfiguration.class} => WebMvcConfiguration에서 저 클래스를 안쓴다는 것을 선언?함
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class MyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectApplication.class, args);
	}

}
