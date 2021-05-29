package kr.inhatc.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
// 설정
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	//multipartResolver => 처리할 수 있는 파일 크기,인코드 지정
	@Bean // 메모리에 올리기
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1025); //5MB, 한 파일당 사이즈 정하기
		return commonsMultipartResolver;
	}
	
}
