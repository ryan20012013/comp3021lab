package base;
import java.util.ArrayList;
import java.util.Objects;

public class Folder {
	private ArrayList<Note>notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>(); 
	}
	
	public void addNote(Note note){
		notes.add(note);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Note> getNotes(){
		return this.notes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			//System.out.println("01");
			return true;}
		if (obj == null) {
			//System.out.println("02");
			return false;}
		if (getClass() != obj.getClass()) {
			//System.out.println("03");
			return false;}
		//System.out.println("04");
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for(Note note:notes) {
			if (note instanceof TextNote) {
				nText+=1;
			}
			else if (note instanceof ImageNote) {
				nImage+=1;
			}
		}
		
		return name + ":" + nText + ":" + nImage;
	}
	
}
