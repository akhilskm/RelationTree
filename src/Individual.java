import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Individual implements Serializable{
	private String name;
	private boolean genderMale;
	private Individual parent;
	private List<Individual> children;
	private Individual spouse;
	private boolean isLast=false;
	
	public Individual(String name, boolean genderMale, Individual parent, Individual spouse, List<Individual> children) {
		this.name = name;
		this.genderMale = genderMale;
		this.parent = parent;
		this.children = children;
		this.spouse = spouse;
	}
	
	public Individual(){
		this.name = "";
		this.genderMale = true;
		this.parent = null;
		this.children = new ArrayList<Individual>();
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGenderMale() {
		return genderMale;
	}

	public void setGenderMale(boolean genderMale) {
		this.genderMale = genderMale;
	}

	public Individual getParent() {
		return parent;
	}

	public void setParent(Individual parent) {
		this.parent = parent;
	}

	public List<Individual> getChildren() {
		return children;
	}

	public void setChildren(List<Individual> children) {
		this.children = children;
	}
	
	public void addChildren(Individual[] children) {
		for(Individual y:children)
			this.children.add(y);
	}
	
	public void addChildren(Individual child) {
		this.children.add(child);
	}
	
	public void writeToFile(String filename)throws IOException{
		FileOutputStream fout = new FileOutputStream("address.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(this);
		oos.close();
	}
	
	public Individual readFromFile(String filename)throws IOException{
		FileInputStream fin = new FileInputStream("address.ser");
		ObjectInputStream ois = new ObjectInputStream(fin);
		Individual root = null;
		try{
		   root = (Individual) ois.readObject();
		}catch(ClassNotFoundException cnf){
		   System.out.println("Class Not Found");
		}finally{
		   ois.close();
		}
		return root;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public Individual getSpouse() {
		return spouse;
	}

	public void setSpouse(Individual spouse) {
		this.spouse = spouse;
	}
}
