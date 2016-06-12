import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunProgram extends Application{
	private static Individual root;
	
	public static Individual setTree(){
		Individual narayanan = new Individual("Narayanan", true, "Kallamvelli Illam", null, null, null);
		narayanan.setLast(true);
		
		Individual narasimhan = new Individual("Hari", true, "Kallamvelli Illam", narayanan, null, null);
		Individual madhavan = new Individual("Madhavan", true, "Kallamvelli Illam", narayanan, null, null);
		Individual sathi = new Individual("Sathi", false, "Kallamvelli Illam", narayanan, null, null);
		Individual sankaran = new Individual("Sankaran", true, "Kallamvelli Illam", narayanan, null, null);
		Individual vamanan = new Individual("Vamanan", true, "Kallamvelli Illam", narayanan, null, null);
		vamanan.setLast(true);		
		narayanan.setChildren(Arrays.asList(narasimhan,madhavan,sathi,sankaran,vamanan));
		
		Individual sreekanth = new Individual("Sreekanth", true, "Kallamvelli Illam", narasimhan, null, null);
		Individual sreeja = new Individual("Sreeja", false, "Kallamvelli Illam", narasimhan, null, null);
		sreeja.setLast(true);		
		narasimhan.setChildren(Arrays.asList(sreekanth,sreeja));
		
		Individual kartika = new Individual("Kartika", false, "Kallamvelli Illam", sathi, null, null);
		Individual manu = new Individual("Manu", true, "Kallamvelli Illam", sathi, null, null);
		manu.setLast(true);		
		sathi.setChildren(Arrays.asList(kartika,manu));
		
		Individual akhil = new Individual("Akhil", true, "Kallamvelli Illam", madhavan, null, null);
		Individual hari = new Individual("Hari", true, "Kallamvelli Illam", madhavan, null, null);
		Individual saranya = new Individual("Saranya", false, "Kallamvelli Illam", madhavan, null, null);
		saranya.setLast(true);		
		madhavan.setChildren(Arrays.asList(akhil,hari,saranya));
		
		Individual kichu = new Individual("Kichu", true, "Kallamvelli Illam", sankaran, null, null);
		Individual achu = new Individual("Achu", true, "Kallamvelli Illam", sankaran, null, null);
		achu.setLast(true);
		sankaran.setChildren(Arrays.asList(kichu,achu));
		
		Individual udhav = new Individual("Udhav", true, "Kallamvelli Illam", vamanan, null, null);
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

	
public static void main(String args[])throws IOException{
		
		root = readFromFile("address.ser");
		//root = setTree();
		//writeToFile(root, "address.ser");
		printTree(root,"");
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		new RTree().mainstage(stage, root);
	}

}
