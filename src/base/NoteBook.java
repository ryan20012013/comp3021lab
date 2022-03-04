package base;
import java.util.ArrayList;
import java.util.Collections;

public class NoteBook {
	private ArrayList<Folder>  folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
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

