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
		ArrayList<Integer> targetWord_check = new ArrayList<Integer>();
		for(int i = 0; i< word.length;i++) {
			if(word[i].equals("or") || word[i].equals("OR") ) {
				//targetWord.remove(targetWord.size()-1);
				targetWord.set(targetWord.size()-1,targetWord.get(targetWord.size()-1)  + " " + 
						word[i+1].substring(0,1).toUpperCase() + word[i+1].substring(1).toLowerCase() );
				//targetWord_check.add(0);
				i = i+1;
				}
			else {
				targetWord.add(word[i].substring(0,1).toUpperCase() + word[i].substring(1).toLowerCase());
				targetWord_check.add(0);
			}
		}System.out.println();
		for(int i = 0; i< targetWord.size();i++) {
			System.out.println(i +": " + targetWord.get(i) + "|");
		}
		for(int k = 0; k <this.notes.size();k++) {
			System.out.print("Title: " + this.notes.get(k).getTitle() + " |");
			for(int i =0; i<targetWord_check.size();i++) {
				targetWord_check.set(i, 0) ;
			}
			for(int j = 0; j <targetWord.size();j++) {	
				word = targetWord.get(j).split(" ");
				for(int z = 0; z < word.length; z++) {
					//System.out.print("word " + z + " "  +word[z] + " " );
					if(this.notes.get(k).getTitle().contains(word[z].toLowerCase()) ||
							this.notes.get(k).getTitle().contains(word[z].toUpperCase()) || 
							this.notes.get(k).getTitle().contains(word[z])) {
						System.out.print("  <== true  " + "J =" + j);	
						targetWord_check.set(j, 1);
					}
					if (this.notes.get(k) instanceof TextNote) {
						if (((TextNote)this.notes.get(k)).content.contains(word[z].toLowerCase() )||
								((TextNote)this.notes.get(k)).content.contains(word[z].toUpperCase() ) ||
								((TextNote)this.notes.get(k)).content.contains(word[z] )) {
							System.out.print("  <== true  "+ "J =" + j);
							targetWord_check.set(j, 1);
						}}}
				System.out.println();
				}
			int flag = 0;
			for(int i = 0; i< targetWord_check.size();i++) {
				System.out.print("targetWord_check.get(i) "+targetWord_check.get(i));
				if(targetWord_check.get(i) ==0) {
					flag = 1;
					break;
				}
			}
			System.out.println();
			System.out.print("Flag "+flag);
			if(flag != 1) {
				result.add(this.notes.get(k));
				//System.out.println("^^^^^^" + this.notes.get(k).getTitle());
			}
		
		}
		return result;
	}
	
	public boolean removeNote(String title) {
		for(int i= 0; i < this.getNotes().size();i++) {
			if(this.getNotes().get(i).getTitle().compareTo(title)==0) {
				this.getNotes().remove(i);
				return true;
			}
		}
		return false;
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
