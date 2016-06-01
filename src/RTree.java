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
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RTree extends Application{
	private static Individual root;
	
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
		
		TreeView<Individual> tree= new TreeView<Individual>(populate(root));
		tree.setShowRoot(true);	
		tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				TreeItem<Individual> selectedItem = (TreeItem<Individual>) newValue;
	            DetailsDialog.display(selectedItem.getValue());
			}
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().add(tree);
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
