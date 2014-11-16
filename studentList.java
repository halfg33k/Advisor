import java.io.*;

public class studentList{
	Node root;
	Node head;
	File file = new File("studentList.txt"); // file to store the queue information
	BufferedWriter writer; // to write to the file
	
	public studentList(){
		try{
			file.createNewFile();
		} catch(IOException e){ System.out.println("IOException: trace 1"); }
	} // studentList constructor
	
	// add a node to the studentList
	public void addNode(String name, int id, int grade){
		Node node = new Node(name, id, grade);
		
		if (root == null){
			root = node;
			head = root;
			
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(node.toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: trace 2"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		else{
			head.setNext(node);
			head = node;
			
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(node.toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: trace 3"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
	} // addNode
	
	// find a particular node by it's name
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
	
	// remove a particular node by it's name
	public Node removeNode(String name){		
		Node current = root;
		
		if(root.getName() == name)
			root = root.getNext();
		else{
			Node prev = root;
			try{
				while(current.getName() != name){
					if(current != null){
						prev = current;
						current = current.getNext();
					}
					else
						return null;
				}
				
				prev.setNext(current.getNext());
			} catch( NullPointerException e ){ return null; }
		}
		
		return current;
	} // removeNode
	
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
		return "Name: " + name + " \nID: " + id + " \nGrade: " + grade + "\n\n";
	} // toString
} // class Node