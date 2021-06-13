package kr.inhatc.spring;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_spell {

	public static void main(String[] args) throws IOException {
		String message = "맞춤법 틀리면 왜 않되?";
		// String message= " ";
		String command = "python tests.py" + " " + message;

		Process child = Runtime.getRuntime().exec(command);

		InputStreamReader in = new InputStreamReader(child.getInputStream(), "MS949");
		int c = 0;

		while ((c = in.read()) != -1) {

			System.out.print((char) c);
		}

		in.close();
	}

}
