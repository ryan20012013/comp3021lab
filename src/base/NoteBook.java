package base;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class NoteBook implements java.io.Serializable {
	private ArrayList<Folder>  folders;
	
	private static final long serialVersionUID = 1L;
	
	public boolean save(String file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try{
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;}
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file) {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook)in.readObject();
			this.folders = n.folders;
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//folders = new ArrayList<Folder>();
	}
	
	public void sortFolders() {
		for(int i = 0; i < folders.size();i++) {
			folders.get(i).sortNote();
		}
		Collections.sort(folders);
	}
	
	public boolean createTextNote(String foldername, String title) {
		TextNote note= new TextNote(title);
		return insertNote(foldername, note);		
	}
	
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title,content);
		return insertNote(folderName,note);
		
	}
	
	public boolean createImageNote(String foldername, String title) {
		ImageNote note= new ImageNote(title);
		return insertNote(foldername, note);
	}
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	
	public ArrayList<Note> searchNotes(String keywords){
		ArrayList<Note> result = new ArrayList<Note>();  
				for(int k = 0; k <this.folders.size();k++) {
						result.addAll(folders.get(k).searchNotes(keywords));
					}
		//System.out.print("You motherfucker");
		return result;
	}
	
	public boolean insertNote(String foldername, Note note) {
		Folder f = null;
		for (Folder f1: folders) {
			if (f1.getName().equals(foldername) == true) {
				f = f1;
			}
			//System.out.println("==" + f1.equals(foldername));
		}
		if (f == null) {
			f = new Folder(foldername);
			folders.add(f);
			//System.out.println("--");
		}
		for (Note n:f.getNotes()) {
			if (n.equals(note) == true) {
				System.out.println("Creating note " + note.getTitle() + " under folder " + foldername + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
		}
	}

