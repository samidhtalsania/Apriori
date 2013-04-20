import java.util.*;
import java.io.*;


class apriori{

  static int count = 1 ;

	//arrays that will hold item frequent set along with count
	static ArrayList<String> table = new ArrayList<String>() ;
	static ArrayList<Integer> frequency = new ArrayList<Integer>() ;
	
	static ArrayList<String> temp = new ArrayList<String>() ;
	static ArrayList<String> item = new ArrayList<String>();
	static int itemsNum = 0 ;



	public static void main(String[] args) throws IOException{
		
		for (int i=0;i<10 ;i++ ) {
			frequency.add(new Integer(0));
			table.add(Integer.toString(i)) ;
		}

		
		getItems() ;
		//4
		//1234
		//123
		//12
		//1
		
		do{
			generateItemset() ;

			// for (int i=0;i<table.size() ;i++ ) {
			// System.out.println(table.get(i) +"  "+frequency.get(i)) ;
			// }

			pruneItemset() ;

			if (table.size() == 1) {

				break ;
				
			}
		}while(table.size() != 0);

		for (int i=0;i<table.size() ;i++ ) {

			System.out.println("answer: Frequentset:"+table.get(i)+"    Frequency:"+frequency.get(i)) ;
			
		}
	}

	//done

	public static void getItems()throws IOException{
		
			Scanner sc = new Scanner(System.in) ;
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		
			System.out.println("Enter the number of transactions:") ;
			itemsNum = sc.nextInt();
		
		
			System.out.println("enter items in each transactions for "+itemsNum+" items") ;
		
			for (int k=0 ; k<itemsNum ; k++ ) {
			
			item.add(br.readLine()) ;
			
			}
	}
	
	
	

	public static void generateItemset(){

				//arrray that holds broken down string elements
				char individualItem[] ;

			
				if (count==1) {
				
					for (int i=0;i<itemsNum ;i++ ) {
				 	
				 		//break down itemlist into individual item ex) 1,2,3 to [1][2][3]

					 	String temp = item.get(i) ;
					 	individualItem = temp.toCharArray() ;
				 	
					 	for (int j=0;j<individualItem.length ;j++ ) {

					 		check(table,individualItem[j]) ;
				 		
				 	}
				 }

				 count++ ;
			 }

			//generate frequent itemsets
			else{
					join() ;
			} 

	}


	//done
	//generates dataset and count after first pass
	public static void check(ArrayList<String> table , char individualItem){
		for (int i=0;i<table.size() ;i++ ) {

			if ((table.get(i)).equals(Character.toString(individualItem))) {

				int replacedVal = frequency.get(i) ;
				
				replacedVal++ ;
				frequency.set(i,replacedVal) ;
				 
			}
		}
			
		
	}

	//done
	//assuming support of 2
	//removes all the items from table whose support is less thn 2
	public static void pruneItemset(){

		for (int i=table.size()-1; i>=0 ; i-- ) {

			int supportVal = frequency.get(i) ;
			if (supportVal <= 2) {
				
				table.remove(i) ;
				frequency.remove(i) ;
			}
			
		}

		// for (int i=0;i<table.size() ;i++ ) {

		// 	System.out.println(table.get(i) +"  "+frequency.get(i)) ;
			
		// 	}
		
	}


	//joining datasets
	//generate datasets for subsequent passes
	public static void join(){
		String first ;
		String second ;

		for (int i=0;i<table.size()-1  ; i++) {

			first = table.get(i) ;
			
			for(int j=i+1 ; j<table.size() ; j++){

				second = table.get(j);
				String tempstr = first + second ;
				temp.add(tempstr);


			}


		}

		//clear the table so the values can be copied from the temp table which has the new datasets
		table.clear() ;
		table.addAll(temp);
		//clear the temp table 
		temp.clear() ;
		//clear the frequency table to calculate new frequency for new datasets
		frequency.clear();

		//initializing all elements of frequency table to zero
		for (int i=0;i<table.size(); i++ ) {

			frequency.add(new Integer(0)) ;
			
		}

		// for (int i=0;i<table.size() ;i++ ) {

		// 	System.out.println(table.get(i) +"  "+frequency.get(i)) ;
			
		// 	}

		//calculating new frequencies

		calculate_freq() ;
	}

	public static void calculate_freq(){

		

		for (int i=0;i<table.size() ; i++ ) {

			//retrieve string from table to counts its frequency
			String getTablevalue = table.get(i) ;
			
			char tableValue[] ;
			tableValue = getTablevalue.toCharArray() ;
			
			int finalCount = 0 ;


			for(int k=0 ; k<item.size() ; k++){

				String itemTempValue = item.get(k) ;

				int tempCount = 0 ;
				

				for (int j=0 ;j<tableValue.length ;j++ ) {
					// String temp = Character.toString(tableValue[j]) ;
					
					

					for (int l=0 ; l<itemTempValue.length() ; l++) {
						
						if( tableValue[j] == itemTempValue.charAt(l) ){
							tempCount++ ;
							
							break ;
						}
					}


				}

				if (tempCount == tableValue.length) {
					finalCount++ ;
				}


			} 

			//finalCount stores the value of the total number of occurences of // a particular frequentset in the dataset
			//store the value of finalCount in frequency

			frequency.set(i,finalCount) ;
			 

			
		}
	}
}

