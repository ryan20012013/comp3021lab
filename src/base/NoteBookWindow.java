package base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	

	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	
	String currentNote = "";
	
	Stage stage;
	
	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	
	
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load");
		buttonLoad.setPrefSize(100, 20);
		
		
		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contain a Notebook Object!");
				
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)","*.ser");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stage);
				
				if (file != null) {
					loadNoteBook(file);
					ArrayList<String> temp = new ArrayList<String>();
					for(int i = 0; i < noteBook.getFolders().size();i++) {
						temp.add(noteBook.getFolders().get(i).getName());
					}
					ObservableList<String> comboxx = FXCollections.observableArrayList(temp);
					foldersComboBox.setItems(comboxx);
					currentFolder="";
					updateListView();
					
				}
				
			}
			});
		//buttonLoad.setDisable(true);
		
		Button buttonSave = new Button("Save");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				noteBook.save(noteBook.filename);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Successfully saved");
				alert.setContentText("Your file has been saved to file "+ noteBook.filename);
				alert.showAndWait().ifPresent(rs->{
					 if(rs == ButtonType.OK) {
						 System.out.print("Pressed OK.");
					 }
				 });
			}
			});
		//buttonSave.setDisable(true);
		
		
		
		Label Search = new Label("Search");
		Search.setPrefSize(100,20);
		
		TextField textSearch = new TextField();
		textSearch.setPrefSize(200,20);
		
		Button buttonSearch = new Button("Search");
		buttonSearch.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				currentSearch = textSearch.getText();
				textAreaNote.setText("");
				updateListView();
			}
			});
		buttonSearch.setPrefSize(100, 20);
		//buttonLoad.setDisable(true);
		
		Button buttonClearSearch = new Button("Clear Search");
		buttonClearSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			currentSearch = "";
			textSearch.setText("");
			textAreaNote.setText("");
			updateListView();
		}
		});
		buttonClearSearch.setPrefSize(100, 20);
		

		hbox.getChildren().addAll(buttonLoad, buttonSave,Search,textSearch,buttonSearch,buttonClearSearch);

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes
		

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		for(int i = 0; i < this.noteBook.getFolders().size();i++ )	{
			foldersComboBox.getItems().add(this.noteBook.getFolders().get(i).getName());}

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				currentNote = title;
				System.out.print("===============================================================================");
				for(int i = 0; i < noteBook.getFolders().size();i++ )	{
					//if(noteBook.getFolders().get(i).getName().equals(currentFolder)){
						for(int j= 0; j<noteBook.getFolders().get(i).getNotes().size();j++ ) {
							if(noteBook.getFolders().get(i).getNotes().get(j).getTitle().equals(title)) {
								content = (((TextNote)noteBook.getFolders().get(i).getNotes().get(j)).content);
							//}
						}
					}
				}
				textAreaNote.setText(content);
				

			}
		});
		
		Button buttonAddFolder = new Button("Add a Folder");
		buttonAddFolder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int temp = 0 ;
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a foldeer for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()) {
					if (result.get().equals("")) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Warning");
						alert.setContentText("Please input an valid folder name");
						temp++;
						alert.showAndWait().ifPresent(rs->{
							 if(rs == ButtonType.OK) {
								 System.out.print("Pressed OK.");
							 }
						 });
					}
					for(int i = 0; i < noteBook.getFolders().size();i++) {
						if(noteBook.getFolders().get(i).getName().compareTo(result.get()) == 0) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Warning");
							alert.setContentText("You already have a folder named with " + result.get());
							temp++;
							alert.showAndWait().ifPresent(rs->{
								 if(rs == ButtonType.OK) {
									 System.out.print("Pressed OK.");
								 }
							 });
						}
					}
				if(temp ==0) {
					noteBook.addFolder(result.get());
					foldersComboBox.getItems().add(result.get());
					updateListView();}
				}
		}
		});
		buttonAddFolder.setPrefSize(100, 20);
		
		Button buttonAddNote = new Button("Add a Note");
		buttonAddNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int temp = 0;
				if(currentFolder.compareTo("") == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setContentText("Please choose a folder first");
					temp++;
					alert.showAndWait().ifPresent(rs->{
						 if(rs == ButtonType.OK) {
							 System.out.print("Pressed OK.");
						 }
					 });
				}
				else {
					TextInputDialog dialog = new TextInputDialog("Add a Note");
					dialog.setTitle("Input");
					dialog.setHeaderText("Add a new note for current folder:");
					dialog.setContentText("Please enter the name of your note:");
					Optional<String> result = dialog.showAndWait();
					if(result.isPresent() && result.get().compareTo("") != 0) {	
						if(noteBook.createTextNote(currentFolder, result.get())) {
							titleslistView.getItems().add(result.get());
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Successful!");
							alert.setContentText("Insert note " + result.get() +" to folder" + currentFolder + " successfully!");
							alert.showAndWait().ifPresent(rs->{
								 if(rs == ButtonType.OK) {
									 System.out.print("Pressed OK.");
								 }
							 });
						}
					}
				}
		}
		});
		buttonAddNote.setPrefSize(100, 20);
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10); // Gap between nodes
		hbox.getChildren().addAll(new Label("Choose folder: "),buttonAddFolder);
		
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(foldersComboBox);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(buttonAddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder
		if(!currentSearch.equals("")) {
			System.out.print(currentSearch);
			List<Note> output =  noteBook.searchNotes(currentSearch);
			if(output.size() == 0) {
				
			}
			for(int i = 0; i< output.size();i++) {
				list.add(output.get(i).getTitle());
				//content = (((TextNote)output.get(i)).content);
			}
			}
		else {
			for(int i = 0; i < this.noteBook.getFolders().size();i++ )	{
				if(this.noteBook.getFolders().get(i).getName().equals(currentFolder)){
					for(int j= 0; j<this.noteBook.getFolders().get(i).getNotes().size();j++ ) {
						list.add(this.noteBook.getFolders().get(i).getNotes().get(j).getTitle());
					}
				}
			}
		}
		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		
		Button buttonSaveNote = new Button("Save Note");
		buttonSaveNote.setPrefSize(100, 20);
		buttonSaveNote.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				System.out.print("01");
				int temp = 0;
				if(currentFolder.compareTo("") == 0 
						|| currentFolder.compareTo("") == 0) {
					System.out.print("02");
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setContentText("Please select a Folder and a note");
					temp++;
					alert.showAndWait().ifPresent(rs->{
						 if(rs == ButtonType.OK) {
							 System.out.print("Pressed OK.");
						 }
					 });
				}
				else {
					System.out.println("03");
					for(int i = 0; i< noteBook.getFolders().size();i++) {
						System.out.println(noteBook.getFolders().get(i).getName());
						if (noteBook.getFolders().get(i).getName().compareTo(currentFolder) == 0) {
							for(int j = 0; j < noteBook.getFolders().get(i).getNotes().size();j++) {
								System.out.println(noteBook.getFolders().get(i).getNotes().get(j).getTitle());
								System.out.println("currentNote: " + currentNote);
								if(noteBook.getFolders().get(i).getNotes().get(j).getTitle().compareTo(currentNote)==0) {
									System.out.println("saving");
									TextNote.class.cast(noteBook.getFolders().get(i).getNotes().get(j)).content =  textAreaNote.getText();
									updateListView();
									
								}
							}
						}
					}
				}
			}
			});
		Button buttonDeleteNote = new Button("Delete Note");
		buttonDeleteNote.setPrefSize(100, 20);
		buttonDeleteNote.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				int temp = 0;
				if(currentFolder.compareTo(currentNote) == 0 
						&& currentFolder.compareTo("") == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setContentText("Please select a Folder and a note");
					temp++;
					alert.showAndWait().ifPresent(rs->{
						 if(rs == ButtonType.OK) {
							 System.out.print("Pressed OK.");
						 }
					 });
				}
				else {
					for(int i = 0; i< noteBook.getFolders().size();i++) {
						if (noteBook.getFolders().get(i).getName().compareTo(currentFolder) == 0) {
							if(noteBook.getFolders().get(i).removeNote(currentNote)) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Successful!");
								alert.setContentText("Your Note has been successfully removed");
								temp++;
								titleslistView.getItems().remove(currentNote);
								updateListView();
								alert.showAndWait().ifPresent(rs->{
									 if(rs == ButtonType.OK) {
										 System.out.print("Pressed OK.");
									 }
								 });
							}
						}
					}
				}
			}
			});
		
		ImageView saveView = new ImageView(new Image(new File("save.png").toURI().toString()));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);

		ImageView deleteView = new ImageView(new Image(new File("delete.png").toURI().toString()));
		deleteView.setFitHeight(18);
		deleteView.setFitWidth(18);
		deleteView.setPreserveRatio(true);

		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10); // Gap between nodes
		hbox.getChildren().addAll(saveView,buttonSaveNote,deleteView,buttonDeleteNote);
		
		grid.add(hbox, 0, 0);
		grid.add(textAreaNote, 0, 1);
		return grid;
	}

	private void loadNoteBook(File file) {
		NoteBook nb = new NoteBook(file.getName());
		this.noteBook = nb;
	}
	
	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called �he most shocking play in NFL history�� and the Washington Redskins dubbed the �hrowback Special��: the November 1985 play in which the Redskins�� Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award�inning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything�ntil it wasn�. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant� part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether� Daddy Was a Number Runner and Dorothy Allison� Bastard Out of Carolina, Jacqueline Woodson� Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood�he promise and peril of growing up�nd exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}

}
