import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javafx.application.Application;
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
import javafx.stage.Stage;

public class RTree extends Application{
	private static Individual root;
	private TreeView<Individual> tree;
	
	public static void main(String args[])throws IOException{
		
		root = readFromFile("address.ser");
		
		printTree(root,"");
		
		launch(args);
	}
	
	public static Individual setTree(){
		Individual narayanan = new Individual("Narayanan", true, null, null, null);
		narayanan.setLast(true);
		
		Individual narasimhan = new Individual("Narasimhan", true, narayanan, null, null);
		Individual madhavan = new Individual("Madhavan", true, narayanan, null, null);
		Individual sathi = new Individual("Sathi", false, narayanan, null, null);
		Individual sankaran = new Individual("Sankaran", true, narayanan, null, null);
		Individual vamanan = new Individual("Vamanan", true, narayanan, null, null);
		vamanan.setLast(true);		
		narayanan.setChildren(Arrays.asList(narasimhan,madhavan,sathi,sankaran,vamanan));
		
		Individual sreekanth = new Individual("Sreekanth", true, narasimhan, null, null);
		Individual sreeja = new Individual("Sreeja", false, narasimhan, null, null);
		sreeja.setLast(true);		
		narasimhan.setChildren(Arrays.asList(sreekanth,sreeja));
		
		Individual kartika = new Individual("Kartika", false, sathi, null, null);
		Individual manu = new Individual("Manu", true, sathi, null, null);
		manu.setLast(true);		
		sathi.setChildren(Arrays.asList(kartika,manu));
		
		Individual akhil = new Individual("Akhil", true, madhavan, null, null);
		Individual hari = new Individual("Hari", true, madhavan, null, null);
		Individual saranya = new Individual("Saranya", false, madhavan, null, null);
		saranya.setLast(true);		
		madhavan.setChildren(Arrays.asList(akhil,hari,saranya));
		
		Individual kichu = new Individual("Kichu", true, sankaran, null, null);
		Individual achu = new Individual("Achu", true, sankaran, null, null);
		achu.setLast(true);
		sankaran.setChildren(Arrays.asList(kichu,achu));
		
		Individual udhav = new Individual("Udhav", true, vamanan, null, null);
		udhav.setLast(true);
		vamanan.setChildren(Arrays.asList(udhav));
		
		return narayanan;
	}
	
	public static void printTree(Individual root,String prefix){
		System.out.println((prefix.length()==0?"":(prefix + (root.isLast()?"└── " : "├── ")))+root.getName());
		if(root.getChildren()!=null){
			List<Individual> childs = root.getChildren();
			Iterator<Individual> chit = childs.iterator();
			while(chit.hasNext()){
				printTree(chit.next(),prefix+(prefix.length()==0?"":(root.isLast()?"":"│"))+"     ");
			}
		}
	}

	public static void writeToFile(Individual root,String filename) throws IOException{
		FileOutputStream fout = new FileOutputStream(filename);
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(root);
		oos.close();
	}
	
	public static Individual readFromFile(String filename) throws IOException{
		Individual root = null;
		FileInputStream fin = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fin);
		try {
			root = (Individual) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ois.close();
		}
		return root;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Family Tree for "+root.getName());
		
		Button addChildButton = new Button("Add Child");
		final Button addParentButton = new Button("Add Parent");
		addParentButton.setDisable(true);
		
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
					}
					else{
						addParentButton.setDisable(true);
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
					DetailsDialog.display(tree.getSelectionModel().selectedItemProperty().getValue().getValue());
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
					Individual newChild = new Individual("New Child",true,parent,null,null);
					parent.addChildren(newChild);
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
					Individual newparent = new Individual("New Parent",true,null,null,Arrays.asList(person));
					person.setParent(newparent);
					TreeItem<Individual> parentItem = new TreeItem<Individual>(newparent);
					parentItem.getChildren().add(tree.getSelectionModel().getSelectedItem());
					parentItem.setExpanded(true);
					tree.setRoot(parentItem);
				}
			}
		});
		
		Button saveButton = new Button("Save");
		
		BorderPane layout = new BorderPane();
		layout.setCenter(tree);
		HBox options = new HBox();
		options.setAlignment(Pos.CENTER);
		options.setPadding(new Insets(10,10,10,10));
		options.setSpacing(20);
		options.getChildren().addAll(addParentButton, addChildButton, detailsButton,saveButton);
		layout.setBottom(options);
		
		Scene scene = new Scene(layout, 350, 400);
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
