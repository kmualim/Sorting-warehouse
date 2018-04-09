package assignment2;

public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
	
		int mid = (start+end)/2;
		if (start < end) { 
			 mergeSort(start, mid); 
			 mergeSort(mid+1, end);
			 merge(start, mid, end);
		}
}
		
		
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		
			
				int n1 = mid - start + 1; 
				int n2 = end - mid;
				
				/*Creating temp arrays*/
				int L[] = new int [n1]; 
				int R[] = new int [n2];
				
				for (int i=0; i<n1; ++i) {
					L[i] = storage[start+i].height;
				}
				for ( int j=0; j<n2; ++j) {
					R[j] = storage[mid+1+j].height;
				}
				int l=0; int j=0; 
				int k=start; 
				while (l< n1 && j <n2) {
					// Figure out a way to compare the elements in the two arrays
					if(L[l] <= R[j]) {
						storage[k].height = L[l];
						l++;
					}
					else { 
						storage[k].height = R[j];
						j++;
					}
					k++;
				}		
					while(l <n1) {
					storage[k].height = L[l];
					l++;
					k++;
				}
					while(j <n2) {
					storage[k].height = R[j];
					j++;
					k++;
					}
				}
						
					
				
		
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
 
		
		for (int i=0; i< storage.length; i++) {
			if (storage[i].height >= b.height) { 
				if (storage[i].availableLength >= b.length) {
				storage[i].addBox(b);
				return noProblem;
				}
			}
		}
		return problem;
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		
		
		if(b != null) {
			if (b.getClass() == UrgentBox.class) { 
				if (toShipUrgently == null) {
					toShipUrgently = (UrgentBox) b;
					b.previous = null; 
					b.next = null; 
				}
				else {
					toShipUrgently.previous = (UrgentBox) b;
					b.next = toShipUrgently;
					toShipUrgently = (UrgentBox) b; 
					b.previous = null;
				}
			}
		
			if (b.getClass() == Box.class){
				if(toShip == null) { 
					toShip = b;
					b.previous = null;
					b.next = null;
				}
				else {
					toShip.previous = b;
					b.next = toShip; 
					toShip = b;
					b.previous = null;
				}
				return noProblem;
			}
		}
		return problem; 		
}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		
		for (int i=0; i< nbShelves; i++) { 
			 Box del = storage[i].removeBox(identifier);
			 if (del != null) {
				 addToShip(del);
				 return noProblem;
			 }
			}
		return problem;
		}
	
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		
		for (int i=0; i<nbShelves; i++) {
			if (i != position) {
				storage[position].removeBox(b.id);
				addBox(b);
				break;
			}
		}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		for (int i=0; i<nbShelves; i++) {
			
			Box current = storage[i].firstBox;
			while(current !=null) {
				Box temp = current.next;
				moveOneBox(current,i);
				current =temp;
			}
		}
		
	}
}
