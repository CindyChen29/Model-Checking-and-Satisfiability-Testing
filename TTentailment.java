
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TTentailment {

    public static ArrayList<Integer> combine(ArrayList<Integer> list1, ArrayList<Integer> list2){
        for(Integer list:list2) {
            if(!list1.contains(list)) {
                list1.add(list);
            }
        }
        return list1;
    }
    
	public static boolean TTEntails(ArrayList<ArrayList<Integer>> KB, ArrayList<ArrayList<Integer>> alpha,ArrayList<Integer> symbols) {
		//model shows true possible outcomes, we use hashmap to connect the sentence to the result of T/F
		
        Model emptyModel=new Model();

		return TTCheckAll(KB, alpha, symbols, emptyModel);
	}
	
	public static boolean TTCheckAll(ArrayList<ArrayList<Integer>> KB, ArrayList<ArrayList<Integer>> alpha, ArrayList<Integer> symbols, Model model) {
		if(symbols.isEmpty()) {
			if(PLTrue(KB,model))
				return PLTrue(alpha, model);
			else {
				return true;
			}
		}else {
			
			int first=symbols.get(0);
			ArrayList<Integer> rest=new ArrayList<Integer>(symbols.subList(1, symbols.size()));
			return TTCheckAll(KB, alpha, rest, union(first, true, model)) && TTCheckAll(KB, alpha, rest, union(first, false, model));
		}
	}
	
	public static boolean PLTrue(ArrayList<ArrayList<Integer>> KB, Model model) {
		boolean output=false;
		int count=0;
		for(int i=0;i<KB.size();i++) {
			for(int j=0;j<KB.get(i).size();j++) {
			
				int var=KB.get(i).get(j) ;
				
				if(var<0) {
					if(Model.getTruthTable().get(-var)==false) {
						output=true;
					}else  {
						output=false;
					}
				
				}else if(Model.getTruthTable().get(var)==true){
					output=true;
				}
			}
			if(output==true) {
				count++;
				output=false;
			}
		}
		if(count==KB.size())
			return true;
		return output;
	}

	 public static Model union(int first, boolean value, Model model){
		 Model result = new Model();
		 Model.truthTable.put(first, value);
		 Model.truthTable.putAll(Model.truthTable);
		 return result;
	 }
	 
	 public static void output(int caseNum) {
		 if(caseNum==1) {
			 //PQ Case
			 System.out.println("1. Does KB entail Q?");
			 
			 ArrayList<ArrayList<Integer>> KB=new ArrayList<ArrayList<Integer>>();
			 String[] KBInputs= {"1","-1 2"};
			 for(String input:KBInputs) {
				 Model.addClause(Model.readClause(input));
				 KB.add(readClause(input));
			 }
			
			 ArrayList<ArrayList<Integer>> alpha=new ArrayList<ArrayList<Integer>>();
			 String alphaString="2";
			 ArrayList<Integer> a=readClause(alphaString);
			 alpha.add(a);
			 
			 System.out.println("1=P, Q=2");
			 System.out.print("Knowledge Base in CNF: ");
			 for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			 System.out.println();
		
			 //successfully get symbol 
			 ArrayList<Integer> symbolKBArrayList=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayList=a;
			 ArrayList<Integer> symbols=combine(symbolKBArrayList, symbolAlphaArrayList);
			 System.out.print("Symbols: ");
			 for(int i:symbols) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alpha, symbols));
		 }else if(caseNum==2) {
			 //wumpus world
			 System.out.println("2. Wumpus World");
			 ArrayList<ArrayList<Integer>> KB=new ArrayList<ArrayList<Integer>>();
			 String[] KBInputs= {"-1","-2 3 4","-3 2","-4 2","-5 1 6 7","-1 5","-6 5","-7 5","-8 1 6 9","-1 8","-6 8","-9 8"};
			 for(String input:KBInputs) {
				 Model.addClause(Model.readClause(input));
				 KB.add(readClause(input));
			 }
			 System.out.println("1=P11, 2=b11, 3=P12, 4=P21, 5=B21, 6=P22, 7=P31, 8=B12, 9=P13");
			 System.out.println();

			 //Move 1
			 String KBMove1="-2";
			 Model.addClause(Model.readClause(KBMove1));
			 KB.add(readClause(KBMove1));
			 System.out.print("Knowledge Base in CNF: ");
			 for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			 System.out.println();
			 System.out.println();
			 
			 System.out.println("a1. Does KB entail not P12?");
			 ArrayList<ArrayList<Integer>> alphaA1=new ArrayList<ArrayList<Integer>>();
			 String alphaStringA1="-3";
			 ArrayList<Integer> A1=readClause(alphaStringA1);
			 alphaA1.add(A1);
			 ArrayList<Integer> symbolKBArrayListA1=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListA1=new ArrayList<Integer>();
			 symbolAlphaArrayListA1.add(3);
			 ArrayList<Integer> symbolsA1=combine(symbolKBArrayListA1, symbolAlphaArrayListA1);
			 System.out.print("Symbols: ");
			 for(int i:symbolsA1) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaA1, symbolsA1));
			 System.out.println();
			 
			 System.out.println("a2. Does KB entail not P21?");
			 ArrayList<ArrayList<Integer>> alphaA2=new ArrayList<ArrayList<Integer>>();
			 String alphaStringA2="-4";
			 ArrayList<Integer> A2=readClause(alphaStringA2);
			 alphaA2.add(A2);
			 ArrayList<Integer> symbolKBArrayListA2=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListA2=new ArrayList<Integer>();
			 symbolAlphaArrayListA2.add(4);
			 ArrayList<Integer> symbolsA2=combine(symbolKBArrayListA2, symbolAlphaArrayListA2);
			 System.out.print("Symbols: ");
			 for(int i:symbolsA2) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaA2, symbolsA2));
			 System.out.println();
			 
			 System.out.println("a3. Does KB entail not P22?");
			 ArrayList<ArrayList<Integer>> alphaA3=new ArrayList<ArrayList<Integer>>();
			 String alphaStringA3="6";
			 ArrayList<Integer> A3=readClause(alphaStringA3);
			 alphaA3.add(A3);
			 ArrayList<Integer> symbolKBArrayListA3=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListA3=new ArrayList<Integer>();
			 symbolAlphaArrayListA3.add(6);
			 ArrayList<Integer> symbolsA3=combine(symbolKBArrayListA3, symbolAlphaArrayListA3);
			 System.out.print("Symbols: ");
			 for(int i:symbolsA3) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaA3, symbolsA3));
			 System.out.println();
			 
			 System.out.println("a4. Does KB entail not P22?");
			 ArrayList<ArrayList<Integer>> alphaA4=new ArrayList<ArrayList<Integer>>();
			 String alphaStringA4="-6";
			 ArrayList<Integer> A4=readClause(alphaStringA4);
			 alphaA4.add(A4);
			 ArrayList<Integer> symbolKBArrayListA4=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListA4=new ArrayList<Integer>();
			 symbolAlphaArrayListA4.add(6);
			 ArrayList<Integer> symbolsA4=combine(symbolKBArrayListA4, symbolAlphaArrayListA4);
			 System.out.print("Symbols: ");
			 for(int i:symbolsA4) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaA4, symbolsA4));
			 System.out.println();
	
			 
			 //Move 2
			 String KBMove2="5";
			 Model.addClause(Model.readClause(KBMove2));
			 KB.add(readClause(KBMove2));
			 System.out.print("Knowledge Base in CNF: ");
			 for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			 System.out.println();
			 System.out.println();
			 
			 System.out.println("b1. Does KB entail P22 V P31?");
			 ArrayList<ArrayList<Integer>> alphaB1=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB1="6 7";
			 ArrayList<Integer> B1=readClause(alphaStringB1);
			 alphaB1.add(B1);
			 ArrayList<Integer> symbolKBArrayListB1=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB1=new ArrayList<Integer>();
			 symbolAlphaArrayListB1.add(6);
			 symbolAlphaArrayListB1.add(7);

			 ArrayList<Integer> symbolsB1=combine(symbolKBArrayListB1, symbolAlphaArrayListB1);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB1) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB1, symbolsB1));
			 System.out.println();
			 
			 System.out.println("b2. Does KB entail P22?");
			 ArrayList<ArrayList<Integer>> alphaB2=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB2="6";
			 ArrayList<Integer> B2=readClause(alphaStringB2);
			 alphaB2.add(B2);
			 ArrayList<Integer> symbolKBArrayListB2=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB2=new ArrayList<Integer>();
			 symbolAlphaArrayListB2.add(6);
			 ArrayList<Integer> symbolsB2=combine(symbolKBArrayListB2, symbolAlphaArrayListB2);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB2) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB2, symbolsB2));
			 System.out.println();
			 
			 System.out.println("b3. Does KB entail not P22?");
			 ArrayList<ArrayList<Integer>> alphaB3=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB3="-6";
			 ArrayList<Integer> B3=readClause(alphaStringB3);
			 alphaB3.add(B3);
			 ArrayList<Integer> symbolKBArrayListB3=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB3=new ArrayList<Integer>();
			 symbolAlphaArrayListB3.add(6);
			 ArrayList<Integer> symbolsB3=combine(symbolKBArrayListB3, symbolAlphaArrayListB3);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB3) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB3, symbolsB3));
			 System.out.println();
			 
			 System.out.println("b4. Does KB entail P31?");
			 ArrayList<ArrayList<Integer>> alphaB4=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB4="7";
			 ArrayList<Integer> B4=readClause(alphaStringB4);
			 alphaB4.add(B4);
			 ArrayList<Integer> symbolKBArrayListB4=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB4=new ArrayList<Integer>();
			 symbolAlphaArrayListB4.add(7);
			 ArrayList<Integer> symbolsB4=combine(symbolKBArrayListB4, symbolAlphaArrayListB4);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB4) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB4, symbolsB4));
			 System.out.println();
			 
			 System.out.println("b5. Does KB entail not P31?");
			 ArrayList<ArrayList<Integer>> alphaB5=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB5="-7";
			 ArrayList<Integer> B5=readClause(alphaStringB5);
			 alphaB5.add(B5);
			 ArrayList<Integer> symbolKBArrayListB5=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB5=new ArrayList<Integer>();
			 symbolAlphaArrayListB5.add(7);
			 ArrayList<Integer> symbolsB5=combine(symbolKBArrayListB5, symbolAlphaArrayListB5);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB5) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB5, symbolsB5));
			 System.out.println();
			 
			 
			 //Move 4
			 String KBMove3="-8";
			 Model.addClause(Model.readClause(KBMove3));
			 KB.add(readClause(KBMove3));
			 System.out.print("Knowledge Base in CNF: ");
			 for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			 System.out.println();
			 System.out.println();
			 
			 System.out.println("c1. Does KB entail not P22?");
			 ArrayList<ArrayList<Integer>> alphaC1=new ArrayList<ArrayList<Integer>>();
			 String alphaStringC1="-6";
			 ArrayList<Integer> C1=readClause(alphaStringC1);
			 alphaC1.add(C1);
			 ArrayList<Integer> symbolKBArrayListC1=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListC1=new ArrayList<Integer>();
			 symbolAlphaArrayListC1.add(6);
			 ArrayList<Integer> symbolsC1=combine(symbolKBArrayListC1, symbolAlphaArrayListC1);
			 System.out.print("Symbols: ");
			 for(int i:symbolsC1) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaC1, symbolsC1));
			 System.out.println();
			 
			 System.out.println("c2. Does KB entail P31?");
			 ArrayList<ArrayList<Integer>> alphaC2=new ArrayList<ArrayList<Integer>>();
			 String alphaStringC2="7";
			 ArrayList<Integer> C2=readClause(alphaStringC2);
			 alphaC2.add(C2);
			 ArrayList<Integer> symbolKBArrayListC2=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListC2=new ArrayList<Integer>();
			 symbolAlphaArrayListC2.add(7);
			 ArrayList<Integer> symbolsC2=combine(symbolKBArrayListC2, symbolAlphaArrayListC2);
			 System.out.print("Symbols: ");
			 for(int i:symbolsC2) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaC2, symbolsC2));
			 System.out.println();

		 }else if(caseNum==3) {
			 //unicorn 
			 System.out.println("3. Russell & Norvig");
			 
			 ArrayList<ArrayList<Integer>> KB=new ArrayList<ArrayList<Integer>>();
			 String[] KBInputs= {"-1 2","1 -2","1 3","-2 4","-3 4","-4 5"};
			 for(String input:KBInputs) {
				 Model.addClause(Model.readClause(input));
				 KB.add(readClause(input));
			 }
			 
		
			 System.out.println("1=Mythical, 2=Immortal, 3=Mammal, 4=Horned, 5=Magical");
			 System.out.print("Knowledge Base in CNF: ");
			 for(int i=0;i<KB.size();i++) {
				 System.out.print(KB.get(i));
			 }
			 System.out.println();
			 System.out.println();
			 
			 //Case a
			 System.out.println("a. Does KB entail Mythical?");
			 ArrayList<ArrayList<Integer>> alphaA=new ArrayList<ArrayList<Integer>>();
			 String alphaStringA="1";
			 ArrayList<Integer> a=readClause(alphaStringA);
			 alphaA.add(a);
			 
			 ArrayList<Integer> symbolKBArrayListA=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListA=a;
			 ArrayList<Integer> symbolsA=combine(symbolKBArrayListA, symbolAlphaArrayListA);
			 System.out.print("Symbols: ");
			 for(int i:symbolsA) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaA, symbolsA));
			 System.out.println();

			 //Case b
			 System.out.println("b. Does KB entail Magical?");
			 ArrayList<ArrayList<Integer>> alphaB=new ArrayList<ArrayList<Integer>>();
			 String alphaStringB="5";
			 ArrayList<Integer> b=readClause(alphaStringB);
			 alphaB.add(b);
			 
			 ArrayList<Integer> symbolKBArrayListB=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListB=b;
			 ArrayList<Integer> symbolsB=combine(symbolKBArrayListB, symbolAlphaArrayListB);
			 System.out.print("Symbols: ");
			 for(int i:symbolsB) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaB, symbolsB));
			 System.out.println();

			 //Case c
			 System.out.println("c. Does KB entail Horned?");
			 ArrayList<ArrayList<Integer>> alphaC=new ArrayList<ArrayList<Integer>>();
			 String alphaStringC="4";
			 ArrayList<Integer> c=readClause(alphaStringC);
			 alphaC.add(c);
			 
			 ArrayList<Integer> symbolKBArrayListC=Model.getSymbol();
			 ArrayList<Integer> symbolAlphaArrayListC=c;
			 ArrayList<Integer> symbolsC=combine(symbolKBArrayListC, symbolAlphaArrayListC);
			 System.out.print("Symbols: ");
			 for(int i:symbolsC) {
				 System.out.print(i+" ");
			 }
			 System.out.println();
			 System.out.println("Entailment Result: "+TTEntails(KB, alphaC, symbolsC));
			 
		 }
	 }
	 
	 public static void main(String[] args){
		 System.out.println("Part 2:");
		 System.out.println();

		 output(1);
		 System.out.println("----------------------------------------------------------------------------------------");
		 output(2);
		 System.out.println("----------------------------------------------------------------------------------------");
		 output(3);
		
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
		 
