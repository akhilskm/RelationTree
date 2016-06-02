import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
			addChildren(y);
	}
	
	public void addChildren(Individual child) {
		if(children==null){
			setChildren(Arrays.asList(child));
		}
		else{
			Individual[] origChildren = (Individual[]) children.toArray();
			Individual[] newChildren = new Individual[origChildren.length+1];
			for(int i = 0;i<origChildren.length;i++){
				newChildren[i]=origChildren[i];
			}
			newChildren[origChildren.length]=child;
			setChildren(Arrays.asList(newChildren));
		}
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

