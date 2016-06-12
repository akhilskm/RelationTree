import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RTree{

	private TreeView<Individual> tree;
	private Individual root;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void mainstage(final Stage stage, Individual rootnode) throws Exception {
		// TODO Auto-generated method stub
		root = rootnode;
		
		stage.setTitle("Family Tree for "+root.getIllam());
		
		Button addChildButton = new Button("Add Child");
		final Button addParentButton = new Button("Add Parent");
		addParentButton.setDisable(true);
		final Button deleteButton = new Button("Delete");
		
		tree = new TreeView<Individual>(populate(root));
		tree.setShowRoot(true);	
		tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue!=null){
					TreeItem<Individual> selectedItem = (TreeItem<Individual>) newValue;
					if(selectedItem.getValue() == root){
						addParentButton.setDisable(false);
						deleteButton.setDisable(true);
					}
					else{
						addParentButton.setDisable(true);
						deleteButton.setDisable(false);
					}
				}
			}
		});

		Button detailsButton = new Button("Details");
		detailsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(tree.getSelectionModel().selectedItemProperty().getValue()!=null){
					try {
						//System.out.println(tree.getSelectionModel().getSelectedItem().getValue().getInfo());
						DetailsDialog.display(tree.getSelectionModel().selectedItemProperty().getValue().getValue());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tree.getSelectionModel().selectedItemProperty().getValue().setExpanded(!tree.getSelectionModel().selectedItemProperty().getValue().isExpanded());
					tree.getSelectionModel().selectedItemProperty().getValue().setExpanded(!tree.getSelectionModel().selectedItemProperty().getValue().isExpanded());
				}
			}
		});
		
		addChildButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(tree.getSelectionModel().selectedItemProperty().getValue()!=null){
					Individual parent = tree.getSelectionModel().selectedItemProperty().getValue().getValue();
					Individual newChild = new Individual("New Child",true,parent.getIllam(),parent,null,null);
					parent.addChildren(newChild);
					parent.getSpouse().addChildren(newChild);
					try {
						DetailsDialog.display(newChild);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tree.getSelectionModel().selectedItemProperty().getValue().getChildren().add(new TreeItem<Individual>(newChild));
				}
			}
		});
		
		addParentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(tree.getSelectionModel().selectedItemProperty().getValue()!=null){
					Individual person = tree.getSelectionModel().selectedItemProperty().getValue().getValue();
					Individual newparent = new Individual("New Parent",true,person.getIllam(),null,null,Arrays.asList(person));
					person.setParent(newparent);
					try {
						DetailsDialog.display(newparent);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					TreeItem<Individual> parentItem = new TreeItem<Individual>(newparent);
					parentItem.getChildren().add(tree.getSelectionModel().getSelectedItem());
					parentItem.setExpanded(true);
					tree.setRoot(parentItem);
					root = newparent;
					stage.setTitle("Family Tree for "+root.getName());
				}
			}
		});
		
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TreeItem<Individual> selectedItem = tree.getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Individual selected = tree.getSelectionModel().getSelectedItem().getValue();
					Individual parent = selected.getParent();
					if(selected.isLast()){
						int index = parent.getChildren().indexOf(selected);
						if(index>0)
							parent.getChildren().get(index-1).setLast(true);
					}
					parent.removeChild(selected);
					selected=null;
					selectedItem.getParent().getChildren().remove(selectedItem);
				}
				
			}
		});
		
		Button saveButton = new Button("Save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					RunProgram.writeToFile(root, "address.ser");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Button find = new Button("Search");
		find.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String s = DetailsDialog.inputDialog("Enter Search Key");
				if(s.length()>0)
					TraverseTree.search(s, root);
			}
		});
		BorderPane layout = new BorderPane();
		layout.setCenter(tree);
		HBox options = new HBox();
		options.setAlignment(Pos.CENTER);
		options.setPadding(new Insets(5,5,5,5));
		options.setSpacing(10);
		options.getChildren().addAll(addParentButton, addChildButton, detailsButton);
		HBox moreoptions = new HBox();
		moreoptions.setAlignment(Pos.CENTER);
		moreoptions.setPadding(new Insets(5,5,5,5));
		moreoptions.setSpacing(10);
		moreoptions.getChildren().addAll(deleteButton, saveButton, find);
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		box.getChildren().addAll(options,moreoptions);
		layout.setBottom(box);
		
		Scene scene = new Scene(layout, 400, 400);
		stage.setScene(scene);
		stage.show();
	}
	
	public static TreeItem<Individual> populate(Individual rootnode){
		TreeItem<Individual> rootitem,child;
		
		rootitem = new TreeItem<>(rootnode);
		rootitem.setExpanded(true);
		
		if(rootnode.getChildren()!=null){
			List<Individual> childs = rootnode.getChildren();
			Iterator<Individual> chit = childs.iterator();
			while(chit.hasNext()){
				child = populate(chit.next());
				child.setExpanded(false);
				rootitem.getChildren().add(child);
			}
		}
		return rootitem;
	}
}
