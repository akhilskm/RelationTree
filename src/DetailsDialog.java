import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsDialog {
	public static void display(Individual rootnode){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Details");
		
		Label nameText = new Label("Name");
		nameText.setMinWidth(100);
		Label name = new Label(":  "+rootnode.getName());
		
		Label genderText = new Label("Gender");
		genderText.setMinWidth(100);
		Label gender = new Label(":  "+(rootnode.isGenderMale()?"Male":"Female"));
		
		Label spouseText = new Label(rootnode.isGenderMale()?"Wife":"Husband");
		spouseText.setMinWidth(100);
		Label spouse = new Label(":  "+(rootnode.getSpouse()==null?"Nil":rootnode.getSpouse().getName()));
		
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(8);
		layout.setHgap(10);
		
		layout.add(nameText, 0, 0);
		layout.add(name, 1, 0);
		layout.add(genderText, 0, 1);
		layout.add(gender, 1, 1);
		layout.add(spouseText, 0, 2);
		layout.add(spouse, 1, 2);
		
		Scene scene = new Scene(layout,300,300);
		window.setScene(scene);
		window.showAndWait();
	}
}
