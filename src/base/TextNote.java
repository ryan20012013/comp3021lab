package base;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextNote extends Note {
	public String content;
	
	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath) {
		String result = "";
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(absolutePath);
			in = new ObjectInputStream(fis);
			TextNote n = (TextNote) in.readObject();
			result = n.content;
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void exportTextToFile(String pathFolder) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		if(pathFolder == "") {
			pathFolder = ".";
		}
		try {	
			File file = new File(pathFolder + File.separator + this.getTitle().replaceAll(" ", "_") +".txt");
			fos = new FileOutputStream(file.getAbsolutePath());
			out = new ObjectOutputStream(fos);
			System.out.print(this.content);
			out.writeObject(this);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public TextNote(String title) {
		super(title);
	}
	
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}
	
}
