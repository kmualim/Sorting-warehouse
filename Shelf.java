package assignment2;

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE 
		Box a = firstBox; 
		if (a == null) {
			firstBox = b;
			lastBox = firstBox;
			
		}
		else { 
			lastBox.next = b;
			b.previous = lastBox;
			lastBox = b; 
			}	
		availableLength -= b.length;
		}
	
		
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		Box a = firstBox;   
		while ( a != null) { 
			if (a.id == identifier) { 
				//If head does not exist, break out of logic
				if (a == firstBox && a == lastBox) { 
					firstBox = null; 
					lastBox = null; 
				}
				// if my box is at the start & there are more boxes
				else if (a == firstBox && a.next != null) { 
						firstBox = a.next;
						a.next.previous = null;
					}
		
				else if (a.next != null && a.previous != null) {
					a.previous.next = a.next; 
					a.next.previous = a.previous;
				}
				else if (a == lastBox && a.previous !=null ) {
						lastBox = a.previous; 
						a.previous.next = null;
					} 
				availableLength += a.length;
				a.previous = null; 
				a.next = null; 
				return a;
				}
			a = a.next;
			}
		return a;
	}
}
		
		

