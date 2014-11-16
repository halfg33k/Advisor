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
	
	public Node getNode(String name){
		Node current = root;
		
		while(current.getName() != name){
			current = current.getNext();
			
			if(current == null)
				return null;
			else if(current.getName() == name)
				break;
			else
				continue;
		}
		
		return current;
	} // getNode
	
	private String listAll(){
		Node current = root;
		String studentInfo = current.toString();
		
		while(current.getNext() != null){
			current = current.getNext();
			
			studentInfo += current.toString();
		}
		
		return studentInfo;
	} // listAll
	
	public String toString(){
		return listAll();
	} // toString
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
	
	public String getName(){
		return name;
	}
	
	// return the student's information
	public String toString(){
		return "Name: " + name + "\nID: " + id + "\nGrade: " + grade + "\n\n";
	} // toString
} // class Node