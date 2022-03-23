
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
	static ArrayList<Integer> clause =new ArrayList<Integer>();
	static HashMap<Integer, Boolean> truthTable  = new HashMap<Integer, Boolean>();
	static ArrayList<Integer> symbol=new ArrayList<Integer>();
	 
	public Model(){

	}
	 public static void add(int key,boolean value) {
		 truthTable.put(key,value);
	 }
	 
	 public ArrayList<Integer> getsymbol(){	
			return this.symbol;
	}
	 
	 public static void addClause(ArrayList<Integer> newClause) {
		 for(int i=0; i<newClause.size();i++) {
			 if(newClause.get(i)<0) {
				 truthTable.put(-1*newClause.get(i), null);
			 }else {
				 truthTable.put(newClause.get(i), null);
			 }
		
		 }
		 symbol=new ArrayList<Integer>(truthTable.keySet());
	 }
	
	 
	 public static HashMap<Integer, Boolean> getTruthTable(){
		 return truthTable;
	 }
	 public static ArrayList<Integer> getSymbol(){
		 return symbol;
	 }
	 
	 public static ArrayList<Integer> readClause(String clauseLine) {
			String[] splittedClause=clauseLine.split("\\s");
			
			ArrayList<Integer> clauseIntegers=new ArrayList<Integer>();
			for(int i=0;i<splittedClause.length;i++) {
					int a=Integer.parseInt(String.valueOf(splittedClause[i]));  
					if(a!=0) {
						clauseIntegers.add(a);
				}
			}
			
			return clauseIntegers;
		
	 }
}