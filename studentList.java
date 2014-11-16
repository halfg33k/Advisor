public class studentList{
	Node root;
	Node head;
	
	public void addStudent(String name, int id, int grade){
		Node node = new Node(name, id, grade);
		
		if (root == null){
			root = node;
			head = root;
		}
		else{
			head.setNext(node);
			head = node;
		}
	} // addStudent
	
	/* Insert a method that will return a list containing all
	 * nodes in the queue.
	 */
} // class studentList

class Node{
	private String name;
	private int id;
	private int grade;
	private Node next;
	
	public Node(){
		name = "John Doe";
		id = -1;
		grade = -1;
		next = null;
	} // Node constructor
	
	public Node(String name, int id, int grade){
		this.name = name;
		this.id = id;
		this.grade = grade;
		next = null;
	} // Node constructor
	
	public Node getNext(){
		return next;
	} // getNext
	
	public void setNext(Node next){
		this.next = next;
	} // setNext
	
	// return the student's information
	public String toString(){
		return "Name: " + name + "\nID: " + id + "\nGrade: " + grade;
	} // toString
} // class Node