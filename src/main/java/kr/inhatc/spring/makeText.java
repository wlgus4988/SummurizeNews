package kr.inhatc.spring;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class makeText {

	public static void main(String[] args) throws IOException {
		String content = "근육이 커지기 위해서는";

		String command = "python test_KoGPT2.py" + " " + content;

		Process child = Runtime.getRuntime().exec(command);

		String str = "";

		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");

		int c = 0;

		while ((c = in.read()) != -1) {

			ArrayList<Character> arrays = new ArrayList<Character>();
			arrays.add((char) c);

			for (Character array : arrays) {
				str += array;
			}

		}
		
		System.out.println(str);

		in.close();
	}
}
