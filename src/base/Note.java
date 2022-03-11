package base;

import java.util.Objects;
import java.util.Date;
import java.util.*; 

public class Note implements Comparable<Note>, java.io.Serializable {
		
		private	Date date;
		private String title;
		
		@Override
		public int compareTo(Note o) {
			return o.date.compareTo(this.date);
		}
		
		Note(String title) {
			this.title = title;
			date = new Date(System.currentTimeMillis());
		}
		
		public String getTitle() {
			return title;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(title);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;}
			if (obj == null) {
				return false;}
			Note other = (Note) obj;
			return Objects.equals(title, other.title);
		}
		
		public String toString() {
			return date.toString() + "\t" + title ;
		}
}



