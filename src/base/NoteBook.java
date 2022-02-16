package base;
import java.util.ArrayList;

public class NoteBook {
	private ArrayList<Folder>  folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String foldername, String title) {
		TextNote note= new TextNote(title);
		return insertNote(foldername, note);		
	}
	
	public boolean createImageNote(String foldername, String title) {
		ImageNote note= new ImageNote(title);
		return insertNote(foldername, note);
	}
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}
	
	public boolean insertNote(String foldername, Note note) {
		Folder f = null;
		for (Folder f1: folders) {
			if (f1.getName().equals(foldername) == true) {
				//System.out.println(foldername);
				//System.out.println("adsadasda");
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

