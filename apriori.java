import java.io.*;


class apriori{

	static int count = 1 ;

	//arrays that will hold item frequent set along with count
	static ArrayList<String> table = new ArrayList<>() ;
	static ArrayList<Integer> frequency = new ArrayList<>() ;
	
	static ArrayList<String> temp = new ArrayList<>() ;
	static ArrayList<String> item = new ArrayList<>();
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
		int x = 0 , y = 0 ;
		do{
			x = table.size() ;
			generateItemset() ;

			for (int i=0;i<table.size() ;i++ ) {
			System.out.println(table.get(i) +"  "+frequency.get(i)) ;
			}

			pruneItemset() ;
			y = table.size() ;
			System.out.println(x+"-------------------#################--------------------------"+y) ;
			
		}while(  table.size() != 1  );

		for (int i=0;i<table.size() ;i++ ) {

			System.out.println("answer: Frequentset:"+table.get(i)+"    Frequency:"+frequency.get(i)) ;
			
		}
	}

	//done

	public static void getItems()throws IOException{
		
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
		
			System.out.println("Enter the number of transactions:") ;
			itemsNum = Integer.parseInt(br.readLine()) ;
		
		
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
			if (supportVal < 2) {
				
				table.remove(i) ;
				frequency.remove(i) ;
			}
			
		}

		// for (int i=0;i<table.size() ;i++ ) {

		// 	System.out.println(table.get(i) +"  "+frequency.get(i)+"-->pit") ;
			
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
				
				tempstr = checkUnique(tempstr) ;
				
				// System.out.println(tempstr+"	"+tempstr.length()+"-->join") ;
				temp.add(tempstr);
				// System.out.println(tempstr+"	"+tempstr.length()+"-->join") ;

			}


		}
		table.clear() ;
		table.addAll(temp);
		temp.clear() ;
		remove_duplicate() ;
		frequency.clear();

		//initializing all elements of frequency table to zero
		for (int i=0;i<table.size(); i++ ) {

			frequency.add(new Integer(0)) ;
			
		}
		
		calculate_freq() ;
	}

	

	public static String checkUnique(String temp) {


			char tempStr[] = temp.toCharArray() ;
			//   12131213
			for (int i=0 ; i<tempStr.length-1 ;i++ ) {
				
				for (int j=i+1 ; j<tempStr.length ; j++ ) {
					
					// if (tempStr[j] == '0') {
					// 	break ;
					// }

					if ( tempStr[i] == tempStr[j] ) {
						tempStr[j] = '-' ;
					}
				}
			}

			temp = new String(tempStr) ;
			String split[] = temp.split("-") ;
			temp = "";
			for (String s : split ) {

				temp += s ;
				
			}

			return temp ;
		}


		public static void calculate_freq(){
				
				int c = 0 ;

				for ( String  tableVal : table ) {
						
						
						int tempFrequency = 0 ;
						
						for ( String itemVal : item ) {
							int tempCount = 0 ;
							for ( char ch : tableVal.toCharArray() ){

									

									if ( itemVal.contains(Character.toString(ch)) ) {
											tempCount++ ;									
									}
							}

							
							
							
							if ( tempCount == tableVal.length() ) {
									tempFrequency++ ;

							}	
						}
				
						

						
						frequency.set(c,tempFrequency) ;
						c++ ;
				}

				
		
		}

		public static void remove_duplicate(){
						
			for ( int i=0 ; i<table.size() ; i++ ) {
		 		char strArray[] = table.get(i).toCharArray() ;
		 		

		 		int sum_first = 0 ;
		 		
		 		for ( int j=0 ; j<strArray.length ; j++ ) {
		 			sum_first += Integer.parseInt(Character.toString(strArray[j])) ;
		 		}

		 		// System.out.println(sum_first) ;

		 		for ( int j=table.size()-1 ; j>i ; j-- ) {
		 			
		 			int sum_second = 0 ;
		 			char strArray_second[] = table.get(j).toCharArray() ;
		 			
		 			for ( int k=0 ; k<strArray_second.length ; k++ ) {
		 				sum_second += Integer.parseInt(Character.toString(strArray_second[k])) ;
		 			}
                                          
		 			if (sum_first == sum_second) {
						// System.out.println("removed:"+table.get(j)+"by:"+table.get(i)) ;
		 				table.remove(j) ;
		 			}

		 		}

		 	}	
		}
		
	
}

