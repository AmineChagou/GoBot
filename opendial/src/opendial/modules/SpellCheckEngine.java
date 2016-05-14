package opendial.modules;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SpellCheckEngine {
	public static String spellCheck(String userInput){
		try{
			ProcessBuilder pb = new ProcessBuilder("python","SpellChecker.py",userInput);
			Process p = pb.start();			 
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String res = new String(in.readLine());
			return res;
		}catch(Exception e){
			System.out.println(e);
			return "";
		}
	}
	
}
