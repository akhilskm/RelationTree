import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsDialog {
	public static void display(final Individual rootnode){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Details");
		
		Label nameText = new Label("Name");
		nameText.setMinWidth(100);
		Label colon1 = new Label(":");
		final TextField name = new TextField(rootnode.getName());
		name.setEditable(false);
		
		Label genderText = new Label("Gender");
		genderText.setMinWidth(100);
		Label colon2 = new Label(":");
		Label gender = new Label((rootnode.isGenderMale()?"Male":"Female"));
		
		Label spouseText = new Label(rootnode.isGenderMale()?"Wife":"Husband");
		spouseText.setMinWidth(100);
		Label colon3 = new Label(":");
		Label spouse = new Label((rootnode.getSpouse()==null?"Nil":rootnode.getSpouse().getName()));
		
		final Button editsave = new Button("Edit");
		editsave.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(editsave.getText().equals("Edit")){
					name.setEditable(true);
					editsave.setText("Save");
				}else{
					rootnode.setName(name.getText());
					name.setEditable(false);
					editsave.setText("Edit");
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
		layout.add(spouseText, 0, 2);
		layout.add(colon3, 1, 2);
		layout.add(spouse, 2, 2);
		
		layout.add(editsave, 2, 3);
		
		Scene scene = new Scene(layout,300,300);
		window.setScene(scene);
		window.showAndWait();
	}
}
