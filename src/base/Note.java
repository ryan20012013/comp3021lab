package base;

import java.util.Objects;
import java.util.Date;

public class Note {
		
		private	Date date;
		private String title;
		
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
			//System.out.println("equal");
			//System.out.println("04 Note Title: " + this.title + " and " + obj);
			if (this == obj) {
				//System.out.println("01");
				return true;}
			if (obj == null) {
				//System.out.println("02");
				return false;}
			/*if (getClass() != obj.getClass()) {
				System.out.println("03");
				return false;}*/
			Note other = (Note) obj;
			return Objects.equals(title, other.title);
		}
}



