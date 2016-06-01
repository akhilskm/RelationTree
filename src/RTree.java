import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RTree {
	public static void main(String args[])throws IOException{
		Individual root = setTree();
		printTree(root,"");
		//example
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
}
