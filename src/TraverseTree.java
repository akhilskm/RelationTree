import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TraverseTree{
	
	private static int hits = 0;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void search(String keyword, Individual root){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Search Results");
		final ListView<Individual> list = new ListView<>();
		searchchildren(keyword, root.getRoot(),list);
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			
			public void changed(ObservableValue arg0, Object oldVal, Object newVal) {
				try{
				DetailsDialog.viewDetails((Individual) newVal);
				list.getSelectionModel().clearSelection();
				}
				catch(NullPointerException npe){}
			}
		});
		VBox box = new VBox();
		Label label = new Label("Search resulted in "+hits+" hits");
		box.getChildren().add(label);
		if(hits>0)
			box.getChildren().add(list);
		box.setSpacing(8);
		box.setPadding(new Insets(10));
		Scene scene = new Scene(box,300,300);
		window.setScene(scene);
		window.showAndWait();
	}

	private static void searchchildren(String keyword, Individual root, ListView<Individual> list) {
		// TODO Auto-generated method stub
		if(root.getName().toLowerCase().contains(keyword.toLowerCase())){
			//System.out.println(root.getInfo());
			list.getItems().add(root);
			hits++;
		}
		if(root.getChildren()!=null){
			List<Individual> children = root.getChildren();
			for(Individual e: children){
				searchchildren(keyword, e, list);
			}
		}
	}
}