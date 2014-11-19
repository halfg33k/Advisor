import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class studentList{
	Node root;
	Node head; // where to insert new nodes
	File tempFile = new File("temp.txt"); // file to temporarily store the queue information
	BufferedWriter writer; // to write to a file
	BufferedReader reader; // to read from a file
	int size = 0;
	
	public studentList(){
		try{
			// this file only exists while the program is running
			// think of it like RAM
			tempFile.createNewFile();
			importStudents("studentList.txt");
		} catch(IOException e){ System.out.println("IOException: studentList constructor"); }
	} // studentList constructor
	
	// import students from a given file
    public void importStudents(String fileName){
        File studFile = new File(fileName);
        Scanner scan;
        
        try{
            scan = new Scanner(studFile);
            scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
            
            while(scan.hasNextLine()){
                addNode(scan.next(), scan.nextInt(), scan.next());
                scan.nextLine();
            }
        }catch(IOException e){ System.out.println("IOException: importStudents"); }
         catch(InputMismatchException e){ System.out.println("InputMismatchException: importStudents"); }
         catch(NoSuchElementException e){}
    } // importStudents
	
	// return the root node
	public Node getRoot(){
		return root;
	} // getRoot
	
	// create a node and add it to the studentList
	public void addNode(String name, int id, String grade){
		Node node = new Node(name, id, grade);
		
		// add node to the end of the queue
		if (root == null){
			root = node;
			head = root;
		}
		else{
			head.setNext(node);
			head = node;
		}
		
		// write the node to the tempFile
		try{
			writer = new BufferedWriter(new FileWriter(tempFile, true));
			writer.write(node.toString());
			writer.newLine();
		} catch(IOException e){ System.out.println("IOException: addNode"); }
		finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		
		size++;
	} // addNode(info)
	
	// insert a given node into the studentList
	public void addNode(Node node){
		String name = node.getName();
		int id = node.getID();
		String grade = node.getGrade();
		
		addNode(name, id, grade);
	} // addNode(node)
	
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
	
	// find a particular node by it's id
	public Node getNode(int id){
		Node current = root;
		
		while(current.getID() != id){
			current = current.getNext();
			
			if(current == null)
				return null;
			else if(current.getID() == id)
				break;
			else
				continue;
		}
		
		return current;
	} // getNode
	
	// find a node by it's place in the queue
	public Node getNode(int index, int unused){
		Node current = root;
		
		for(int i = 0; i < index; i++){
			current = current.getNext();
		}
		
		return current;
	} // getNode
	
	// edit the name of a given node
	public void editNode(int id, String name){
		Node node = getNode(id);
		
		// change the name of the node
		node.setName(name);
		
		removeNode(id); // remove the node with the original name
		addNode(node); // add the node with the new name
	} // editNode(id, name)
	
	// edit the id of a given node
	public void editNode(int id, int newID){
		Node node = getNode(id);
		
		node.setID(newID); // change the id of the given node
		
		removeNode(id); // remove the node with the original id
		addNode(node); // add the node with the new id
	}
	
	// edit the grade of a given node
	public void editNode(int id, String grade, int unused){
		Node node = getNode(id);
		
		// change the grade of the node
		node.setGrade(grade);
		
		removeNode(id); // remove the node with the original grade
		addNode(node); // add the node with the new grade
	} // editNode(id, grade)
	
	// edit both the name and grade of a given node
	public void editNode(int id, String name, String grade){
		Node node = getNode(id);
		
		node.setName(name); // change the name of the node
		node.setGrade(grade); // change the grade of the node
		
		removeNode(id); // remove the node with the original data
		addNode(node); // add the node with the new data
	} // editNode(id, name, grade)
	
	// remove a particular node by it's id
	public Node removeNode(int id){		
		Node current = root;
		File file = new File("temp2.txt");
		
		if(root.getID() == id)
			root = root.getNext();
		else{
			Node prev = root;
			try{
				while(current.getID() != id){
					if(current != null){
						prev = current;
						current = current.getNext();
					}
					else
						return null;
				}
			} catch( NullPointerException e ){ return null; }
			
			prev.setNext(current.getNext());
		}
		
		// rewrite the tempFile without the old data and including the new data
		try{
			file.createNewFile();
			
			reader = new BufferedReader(new FileReader(tempFile));
			writer = new BufferedWriter(new FileWriter(file));
			
			String currentLine;
			
			while((currentLine = reader.readLine()) != null){
				if(currentLine.contains(id + ""))
					continue;
					
				writer.write(currentLine);
				writer.newLine();
			}
		} catch(Exception e){ System.out.println("Exception: removeNode(id)"); }
		finally{try{writer.close(); reader.close();}catch(Exception e){}}
		
		tempFile.delete(); // delete tempFile to free up the name for the new tempFile
		// rename the new tempFile to temp.txt
		if(!(file.renameTo(tempFile)))
			System.out.println("Failed to rename file.");
		
		size--;
		return current;
	} // removeNode
	
	// list all nodes in the queue
	private String listAll(){
		Node current = root;
		String studentInfo = current.toString() + "\n";
		
		while(current.getNext() != null){
			current = current.getNext();
			
			studentInfo += current.toString() + "\n";
		}
		
		return studentInfo;
	} // listAll
		
	// delete the tempFile and transfer its info to studentList.txt
	public void close(){
		File file = new File("studentList.txt");
		
		try{
			file.createNewFile();
			
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(tempFile));
			
			String currentLine;
			
			while((currentLine = reader.readLine()) != null){
				writer.write(currentLine);
				writer.newLine();
			}
		} catch(Exception e){ System.out.println("Exception: close"); }
		finally{try{writer.close(); reader.close();}catch(Exception e){}}
		
		tempFile.delete();
	} // close
	
	// whether the list contains a given id
	public boolean contains(int id){
		for(int i = 0; i < size; i++){
			try{
				if(getNode(i, 0).getID() == id)
					return true;
			} catch(NullPointerException e){ System.out.print("-trace" + i + "-"); }
		}
		
		return false;
	} // contains
	
	// whether the list contains a given name
	public boolean contains(String name){
		for(int i = 0; i < size; i++){
			if(getNode(i).getName() == name)
				return true;
		}
		
		return false;
	} // contains
	
	public int getSize(){
		return size;
	} // getSize
		
	public String toString(){
		return listAll();
	} // toString
} // class studentList

class Node{
	private String name;
	private int id;
	private String grade;
	private Node next; // next node in the queue
	
	// initialize this Node with default values
	public Node(){
		name = "John Doe";
		id = -1;
		grade = null;
		next = null;
	} // Node constructor
	
	// initialize this Node with given values
	public Node(String name, int id, String grade){
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
	} // getName
    
    public void setName(String name){
        this.name = name;
    } // setName
    
    public int getID(){
		return id;
	} // getID
    
    public void setID(int id){
        this.id = id;;
    } // setID
    
    public String getGrade(){
		return grade;
	} // getGrade
    
    public void setGrade(String grade){
        this.grade = grade;
    } // setGrade
	
	// return the student's information
	public String toString(){
		return "Name:" + name + " ID:" + id + " Grade:" + grade;
	} // toString
} // class Node