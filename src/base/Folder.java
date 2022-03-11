package base;
import java.util.ArrayList;
import java.util.Objects;
import java.util.*; 

public class Folder implements Comparable<Folder>, java.io.Serializable {
	private ArrayList<Note>notes;
	private String name;
	
	@Override
	public int compareTo(Folder o) {
		return this.name.compareTo(o.name);
	}
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>(); 
	}
	
	public void sortNote() {
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords){
		//System.out.println("keywords: " + keywords);
		String[] word = keywords.split(" ");
		ArrayList<String> targetWord = new ArrayList<String>();
		ArrayList<Note> result = new ArrayList<Note>();  
		//System.out.println(word[0] + " | " + word[1] + " | " + word[2] + " | " );
		
		for(int i = 0; i< word.length;i++) {
			if(word[i].equals("or") || word[i].equals("OR") ) {
				targetWord.add(word[i-1].substring(0,1).toUpperCase() + word[i-1].substring(1).toLowerCase()  + " " + 
						word[i+1].substring(0,1).toUpperCase() + word[i+1].substring(1).toLowerCase() );
				i = i+1;
				}
		}
		for(int k = 0; k <this.notes.size();k++) {
			int[] orList = {0,0};
			//System.out.print("Title: " + this.notes.get(k).getTitle() + " |");
			for(int j = 0; j <targetWord.size();j++) {	
				word = targetWord.get(j).split(" ");
				for(int z = 0; z < word.length; z++) {
					//System.out.print("word " + z + " "  +word[z] + " " );
					if(this.notes.get(k).getTitle().contains(word[z].toLowerCase()) ||
							this.notes.get(k).getTitle().contains(word[z].toUpperCase()) || 
							this.notes.get(k).getTitle().contains(word[z])) {
						//System.out.print("  <== true  ");
						orList[j] = 1;	
					}
					if (this.notes.get(k) instanceof TextNote) {
						if (((TextNote)this.notes.get(k)).content.contains(word[z].toLowerCase() )||
								((TextNote)this.notes.get(k)).content.contains(word[z].toUpperCase() ) ||
								((TextNote)this.notes.get(k)).content.contains(word[z] )) {
							orList[j] = 1;
							//System.out.print("  <== true  ");
						}}}
				//System.out.println();
				}
			if(orList[1] ==1 && orList[0] == 1) {
				result.add(this.notes.get(k));
			}
		
		}
		return result;
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
