package kr.inhatc.spring;

import java.io.IOException;
import java.io.InputStreamReader;

public class Main_ocr {
	public static void main(String[] args) throws IOException {
		
		String message = "C:/Users/USER/Capston/SummurizeNews/src/main/resources/test.jpg";
		String command = "python image_test.py" + " " + message +" "+"stdout -l kor";

		Process child = Runtime.getRuntime().exec(command);


		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {

			System.out.print((char) c);
		}
		in.close();
	}
}
