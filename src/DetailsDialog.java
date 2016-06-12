import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsDialog {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void display(final Individual rootnode) throws IOException{
		final Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Details of "+rootnode.getName());
		
		final List<String> illams = readListofIllams();
		
		Label nameText = new Label("Name");
		nameText.setMinWidth(100);
		Label colon1 = new Label(":");
		final TextField name = new TextField(rootnode.getName());
		
		Label genderText = new Label("Gender");
		genderText.setMinWidth(100);
		Label colon2 = new Label(":");
		final ChoiceBox<String> gender = new ChoiceBox<>();
		gender.getItems().addAll("Male","Female");
		gender.setValue((rootnode.isGenderMale()?"Male":"Female"));
		
		Label illamText = new Label("Illam");
		illamText.setMinWidth(100);
		Label colon3 = new Label(":");
		final ComboBox<String> illam = new ComboBox<>();
		illam.getItems().addAll(illams);
		illam.setEditable(true);
		illam.setValue(rootnode.getIllam());
		
		
		final Label spouseText = new Label(rootnode.isGenderMale()?"Wife":"Husband");
		spouseText.setMinWidth(100);
		Label colon4 = new Label(":");
		final TextField spouse = new TextField((rootnode.getSpouse()==null?"Nil":rootnode.getSpouse().getName()));
		spouse.setEditable(rootnode.getSpouse()==null);
		
		final Button viewSpouse = new Button("View Spouse Tree");
		viewSpouse.setDisable(rootnode.getSpouse()==null);
		
		gender.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				spouseText.setText(newValue.equals("Male")?"Wife":"Husband");
			}
		});
		
		final Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rootnode.setName(name.getText());
				rootnode.setGenderMale(gender.getSelectionModel().getSelectedItem().equals("Male"));
				
				if(rootnode.getSpouse()==null && (!spouse.getText().equals("Nil"))){
					Individual newspouse = new Individual(spouse.getText(),!rootnode.isGenderMale(),rootnode.getIllam(),null,rootnode,null);
					rootnode.setSpouse(newspouse);
					if(rootnode.getChildren()!=null){
						newspouse.setChildren(rootnode.getChildren());
					}
					try {
						display(newspouse);
						viewSpouse.setDisable(false);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(illams.contains(illam.getValue())){
					rootnode.setIllam(illam.getValue());
				}
				else{
					illams.add(illam.getValue());
					illam.getItems().add(illam.getValue());
					try {
						writeToFile(illams);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		viewSpouse.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					if(rootnode.getSpouse()!=null){
						new RTree().mainstage(new Stage(), rootnode.getSpouse().getRoot());
						window.close();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(8);
		layout.setHgap(10);
		
		layout.add(nameText, 0, 0);
		layout.add(colon1, 1, 0);
		layout.add(name, 2, 0);
		layout.add(genderText, 0, 1);
		layout.add(colon2, 1, 1);
		layout.add(gender, 2, 1);
		layout.add(illamText, 0, 2);
		layout.add(colon3, 1, 2);
		layout.add(illam, 2, 2);
		layout.add(spouseText, 0, 3);
		layout.add(colon4, 1, 3);
		layout.add(spouse, 2, 3);
		
		VBox box = new VBox();
		box.setSpacing(10);
		box.getChildren().addAll(layout,save,viewSpouse);
		box.setAlignment(Pos.TOP_CENTER);
		
		Scene scene = new Scene(box,300,300);
		window.setScene(scene);
		window.showAndWait();
	}
	
	public static void writeToFile(List<String> illams) throws IOException{
		FileOutputStream fout = new FileOutputStream("illamList.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(illams);
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> readListofIllams() throws IOException{
		List<String> illamList = new ArrayList<String>();
		FileInputStream fin = new FileInputStream("illamList.ser");
		ObjectInputStream ois = new ObjectInputStream(fin);
		try {
			illamList = (ArrayList<String>) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ois.close();
		}
		return illamList;
	}
	
	public static String inputDialog(String text){
		final Label s= new Label("");
		final Stage stage = new Stage();
		final TextField inp = new TextField();
		inp.setMaxWidth(150);
		inp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				s.setText(inp.getText());
				stage.close();
			}
		});
		Button b = new Button("Submit");		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(text);
		VBox lay = new VBox();
		lay.getChildren().addAll(inp,b);
		lay.setAlignment(Pos.CENTER);
		lay.setSpacing(8);
		b.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						s.setText(inp.getText());
						stage.close();
					}
				});
		Scene scene = new Scene(lay,300,100);
		stage.setScene(scene);
		stage.showAndWait();
		return s.getText();
	}

	public static void viewDetails(final Individual rootnode) {
		// TODO Auto-generated method stub
		final Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Details of "+rootnode.getName());
		
		Label nameText = new Label("Name");
		nameText.setMinWidth(100);
		Label colon1 = new Label(":");
		final Label name = new Label(rootnode.getName());
		
		Label genderText = new Label("Gender");
		genderText.setMinWidth(100);
		Label colon2 = new Label(":");
		final Label gender = new Label(rootnode.isGenderMale()?"Male":"Female");
		
		Label illamText = new Label("Illam");
		illamText.setMinWidth(100);
		Label colon3 = new Label(":");
		final Label illam = new Label();
		illam.setText(rootnode.getIllam());
		
		final Label spouseText = new Label(rootnode.isGenderMale()?"Wife":"Husband");
		spouseText.setMinWidth(100);
		Label colon4 = new Label(":");
		final Label spouse = new Label((rootnode.getSpouse()==null?"Nil":rootnode.getSpouse().getName()));
		
		Label parentText = new Label("Parent");
		parentText.setMinWidth(100);
		Label colon5 = new Label(":");
		final Label parent = new Label();
		parent.setText(rootnode.getParent()==null?"NA":rootnode.getParent().toString());
		parent.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				if(!parent.getText().equals("NA")){
					viewDetails(rootnode.getParent());
					window.close();
				}
			}
		});
		
		Label childrenText = new Label("Children");
		childrenText.setMinWidth(100);
		Label colon6 = new Label(":");
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(8);
		layout.setHgap(10);
		
		layout.add(nameText, 0, 0);
		layout.add(colon1, 1, 0);
		layout.add(name, 2, 0);
		layout.add(genderText, 0, 1);
		layout.add(colon2, 1, 1);
		layout.add(gender, 2, 1);
		layout.add(illamText, 0, 2);
		layout.add(colon3, 1, 2);
		layout.add(illam, 2, 2);
		layout.add(spouseText, 0, 3);
		layout.add(colon4, 1, 3);
		layout.add(spouse, 2, 3);
		layout.add(parentText, 0, 4);
		layout.add(colon5, 1, 4);
		layout.add(parent, 2, 4);
		layout.add(childrenText, 0, 5);
		layout.add(colon6, 1, 5);

		int noOfChildren = rootnode.getChildren()==null?0:rootnode.getChildren().size();
		Label[] childLabels = new Label[noOfChildren];
		if(noOfChildren>0){
			List<Individual> children = rootnode.getChildren();
			for(int i=0;i<noOfChildren;i++){
				final Individual childaTi = children.get(i); 
				childLabels[i]= new Label(childaTi.getName()+(i==noOfChildren-1?"":","));
				layout.add(childLabels[i], 2, 5+i);
				childLabels[i].setOnMouseClicked(new EventHandler<Event>() {

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub
						viewDetails(childaTi);
						window.close();
					}
				});
			}
		}
		else{
			Label childlabel = new Label("Nil");
			layout.add(childlabel, 2, 5);
		}
		Button viewTree = new Button("View "+rootnode.getName()+"'s Tree");
		viewTree.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					new RTree().mainstage(window, rootnode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Button viewFamilyTree = new Button("View Family Tree");
		viewFamilyTree.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					new RTree().mainstage(window, rootnode.getRoot());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		VBox box = new VBox();
		box.setSpacing(10);
		box.getChildren().addAll(layout,viewTree,viewFamilyTree);
		box.setAlignment(Pos.TOP_CENTER);
		
		Scene scene = new Scene(box,300,300);
		window.setScene(scene);
		window.showAndWait();
	}
}
