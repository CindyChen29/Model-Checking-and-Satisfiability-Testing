
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GSAT {

	static ArrayList<ArrayList<Integer>> KB=new ArrayList<ArrayList<Integer>>();
	static int numVariable=0;
	static int numClauses=0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		System.out.println("Please enter which problem you want to work on (1-7): ");
		System.out.println("1.Problem 1\n2.N-Queens with N=4\n3.N-Queens with N=8\n4.N-Queens with N=12\n5.N-Queens with N=16\n6.quinn\n7.aim-50-1_6-yes1-4\n8.Quit");
		int choice=scanner.nextInt();
		HashMap<Integer, Boolean> result=new HashMap<Integer, Boolean>();
		int maxFlips=0;
		int maxTries=0;
		String filePath="";
		while(choice!=8) {
			KB=new ArrayList<ArrayList<Integer>>();

			if(choice==1) {
				System.out.println("Enter the value for Max-Flips (ex. 5)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (ex. 5)");
				maxTries=scanner.nextInt();
				filePath="Problem1.cnf";
			}else if(choice==2) {
				System.out.println("Enter the value for Max-Flips (ex. 10)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (ex. 10)");
				maxTries=scanner.nextInt();
				filePath="nqueens_4.cnf";
			}else if(choice==3) {
				System.out.println("Enter the value for Max-Flips (ex. 50)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (ex. 10)");
				maxTries=scanner.nextInt();
				filePath="nqueens_8.cnf";
			}else if(choice==4) {
				System.out.println("Enter the value for Max-Flips (ex. 500)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (ex. 100)");
				maxTries=scanner.nextInt();
				filePath="nqueens_12.cnf";
			}else if(choice==5) {
				System.out.println("It takes much time to run.");
				System.out.println("Enter the value for Max-Flips (the larger the better)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (the larger the better)");
				maxTries=scanner.nextInt();
				filePath="nqueens_16.cnf";
			}else if(choice==6) {
				System.out.println("Enter the value for Max-Flips (ex. 10)");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries (ex. 10)");
				maxTries=scanner.nextInt();
				filePath="quinn.cnf";
			}else if(choice==7) {
				System.out.println("Enter the value for Max-Flips");
				maxFlips=scanner.nextInt();
				System.out.println("Enter the value for Max-Tries");
				maxTries=scanner.nextInt();
				filePath="aim-50-1_6-yes1-4.cnf";
			}
			ArrayList<String> clauseArrayList=splitFile(readFile(filePath));
			readIn(clauseArrayList);
			for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			System.out.println();
			result=GSAT(KB,maxFlips,maxTries);
	
			System.out.println(result);
			if(result==null) {
				System.out.println("The local search approach cannot generate a satisfiable set of clauses.");
			}else {
				System.out.println("Congrats!");
			}
			System.out.println();
			System.out.println("Please enter which problem you want to work on (1-7): ");
			System.out.println("1.Problem 1\n2.N-Queens with N=4\n3.N-Queens with N=8\n4.N-Queens with N=12\n5.N-Queens with N=16\n6.quinn.cnf\n7.aim-50-1_6-yes1-4.cnf\n8.Quit");
			choice=scanner.nextInt();
		
		}
	}
	
	//return the whole file into an string 
	public static String readFile(String filepath) {
	      String output="";

		try {
	      File file = new File(filepath);
	      Scanner scanner = new Scanner(file);
	      while (scanner.hasNextLine()) {
	        String data = scanner.nextLine();
	        output+="\n";
	        output+=data;
	      }
	      scanner.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		return output;
	   
	  }
	

	//return the whole file string into arraylist of strings, each of them represents a clause
	public static ArrayList<String> splitFile(String input){
		
		//all info with first clause, clause2, clause3....
		String[] content=input.split("\\s0");
        

		
		String info=content[0];
		String[] firstLine=info.split("\n");
		ArrayList<String> clauseStringArrayList=new ArrayList<String>();
		
		for(int i=0;i<firstLine.length;i++) {
			if(!firstLine[i].isEmpty()) {
			if(firstLine[i].charAt(0)=='p') {
				String[] numberInfo=firstLine[i].split("\\s");
                numVariable = Integer.parseInt(numberInfo[2]);
                numClauses = Integer.parseInt(numberInfo[3]);
			}else if(firstLine[i].charAt(0)!='c') {
                String[] clause = firstLine[i].trim().split("\\s+");
                String clause1="";
                for(int j=0;j<clause.length;j++) {
                	clause1+=clause[j];
                	clause1+=" ";
                }

                clauseStringArrayList.add(clause1);
			}
			}
		}
		for(int i=1;i<content.length;i++) {
			String[] clause = content[i].trim().split("\\s+");
			String clause1="";
            for(int j=0;j<clause.length;j++) {
            	clause1+=clause[j];
            	clause1+=" ";
            }
            clauseStringArrayList.add(clause1);
		}

//		for(int i=0;i<clauseStringArrayList.size();i++) {
//
//		System.out.println("i="+i+"!"+clauseStringArrayList.get(i));
//		}
		return clauseStringArrayList;
	}
	
	public static void readIn(ArrayList<String> clauseArrayList) {
		 
		 
		 for(String input: clauseArrayList) {
			 Model.addClause(Model.readClause(input));
			 KB.add(TTentailment.readClause(input));
		 }
	 }
	
	public static Boolean randomBoolean() {
		Boolean outcome=true;
		if(Math.random()>0.5) {
			outcome=true;
		}else {
			outcome=false;
		}
		return outcome;
	}
	
	public static HashMap<Integer, Boolean> truthAssignment(){
        HashMap<Integer, Boolean> assignmentTable=new HashMap<Integer, Boolean>();
        //randomly assign n variables with true/false values
        for(int i=1;i<=numVariable;i++) {
        	Boolean outcome=randomBoolean();
        	assignmentTable.put(i, outcome);
        }
        return assignmentTable;
	}
	
	public static HashMap<Integer, Boolean> flip(HashMap<Integer, Boolean> currentTable, int key){
		HashMap<Integer, Boolean> newTable=new HashMap<Integer, Boolean>();
		newTable.putAll(currentTable);
		if(currentTable.get(key)==true) {
			newTable.put(key, false);
		}else {
			newTable.put(key, true);
		}
		
		return newTable;
	}
	
	public static HashMap<Integer, Boolean> GSAT(ArrayList<ArrayList<Integer>> KB, int maxFlips, int maxTries){
		Model model=new Model();
		for(int i=0;i<maxTries;i++) {
			System.out.println("Try #: "+(i+1));
			HashMap<Integer, Boolean> currentTable=new HashMap<Integer, Boolean>();
			currentTable=truthAssignment();

			for(int j=0; j<maxFlips;j++) {
				System.out.println("Flip #: "+(j+1));

				if(PLTrue(KB, currentTable)) {
					return currentTable;
				}
			
				int changeKey=findMaxKey(KB, currentTable);
				if(changeKey==0) {
					return currentTable;
				}else {
					System.out.println("Flip Variable "+changeKey);
					currentTable=flip(currentTable, changeKey);
				}
				System.out.println();

			}
		}
		return null;
		

	}
	

	public static int findMaxKey(ArrayList<ArrayList<Integer>> KB, HashMap<Integer, Boolean> currentTable) {
		int max=0;
		int outputKey=0;
		
		for(int key: currentTable.keySet()) {
			int curr=trueClausesNum(KB, currentTable);
	//		System.out.println("current true num: "+curr);

			if(trueClausesNum(KB, currentTable)>=numClauses) {
				return outputKey;
			}
	//		System.out.println("TT before flip: "+currentTable);

			HashMap<Integer, Boolean> tempTable=flip(currentTable, key);
			System.out.println("Assume flip variable "+key);

		//	System.out.println("TT after flip: "+tempTable);

			int num=trueClausesNum(KB, tempTable);
			System.out.println("Number of True Clauses: "+num);
			if(num>max) {
				max=num;
				outputKey=key;
			}
			
			System.out.println();
		}
		
		return outputKey;
	}
	
	public static int trueClausesNum(ArrayList<ArrayList<Integer>> KB, HashMap<Integer, Boolean> currentTable) {
		int count=0;
		boolean output=false;
		
		for(int i=0;i<KB.size();i++) {
			for(int j=0;j<KB.get(i).size();j++) {
				int var=KB.get(i).get(j);
				
				if(var<0) {
					if(currentTable.get(-var)==false)
						output=true;
					
				}else if(currentTable.get(var)==true)
					output=true;
			}
			if(output==true) {
				count++;
				output=false;
			}
		}
		

		
		return count;
	}
	
	public static boolean PLTrue(ArrayList<ArrayList<Integer>> KB, HashMap<Integer, Boolean> currentTable) {
		int count=0;
		boolean output=false;
		for(int i=0;i<KB.size();i++) {
			for(int j=0;j<KB.get(i).size();j++) {
				int var=KB.get(i).get(j);
				
				if(var<0) {
					if(currentTable.get(-var)==false)
						output=true;
					else  
						output=false;
					
					
				}else if(currentTable.get(var)==true)
					output=true;
			}
			if(output==true) {
				count++;
				output=false;
			}
			
		}
		return output;
	}
	
	
	
	
	
	
	
	
	
	
}
