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
		String name, grade;
		int id, totalCreds, majorCreds, upperCreds;
		double totalGPA, majorGPA;
        
        try{
            scan = new Scanner(studFile);
            scan.useDelimiter("Name:| ID:| Grade:| Total GPA:| Major GPA:| Total Credits:| Major Credits:| Upper-Level Credits:|\\n|\\r");
            
            while(scan.hasNextLine()){
				name = scan.next();
				id = scan.nextInt();
				grade = scan.next();
				
				if(scan.hasNextDouble()){
					try{
						totalGPA = scan.nextDouble();
						majorGPA = scan.nextDouble();
						
						if(scan.hasNextInt()){
								totalCreds = scan.nextInt();
								majorCreds = scan.nextInt();
								upperCreds = scan.nextInt();
								
								addNode(name, id, grade, totalGPA, majorGPA, totalCreds, majorCreds, upperCreds);
						}
						else
							addNode(name, id, grade, totalGPA, majorGPA);
					} catch(InputMismatchException ime){ System.out.println("InputMismatchException: importStudents --> GPA"); }
				}
				else
					addNode(name, id, grade);
				
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
	
	// create a node and add it to the list
	public void addNode(String name, int id, String grade, double totalGPA, double majorGPA, int totalCreds, int majorCreds, int upperCreds){
		Node node = new Node(name, id, grade);
		node.setTotalGPA(totalGPA);
		node.setMajorGPA(majorGPA);
		node.setTotalCreds(totalCreds);
		node.setMajorCreds(majorCreds);
		node.setUpperCreds(upperCreds);
		
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
	} // addNode(name, id, grade, totalGPA, majorGPA)
	
	// create a node and add it to the list
	public void addNode(String name, int id, String grade, double totalGPA, double majorGPA){
		addNode(name, id, grade, totalGPA, majorGPA, 0, 0, 0);
	} // addNode(name, id, grade, totalGPA, majorGPA)
	
	// create a node and add it to the studentList
	public void addNode(String name, int id, String grade){
		addNode(name, id, grade, 0.0, 0.0);
	} // addNode(name, id, grade)
	
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
	public void editName(int id, String name){
		Node node = getNode(id);
		
		// change the name of the node
		node.setName(name);
		
		rewrite();
	} // editNode(id, name)
	
	// edit the id of a given node
	public void editID(int id, int newID){
		Node node = getNode(id);
		
		node.setID(newID); // change the id of the given node
		
		rewrite();
	}
	
	// edit the grade of a given node
	public void editGrade(int id, String grade){
		Node node = getNode(id);
		
		// change the grade of the node
		node.setGrade(grade);
		
		rewrite();
	} // editNode(id, grade)
	
	// edit the totalGPA of a given node
	public void editTotalGPA(int id, double totalGPA){
		Node node = getNode(id);
		
		node.setTotalGPA(totalGPA); // change the totalGPA of the given node
		
		rewrite();
	} // editNode(id, totalGPA)
	
	// edit the majorGPA of a given node
	public void editMajorGPA(int id, double majorGPA){
		Node node = getNode(id);
		
		node.setMajorGPA(majorGPA); // change the totalGPA of the given node
		
		rewrite();
	} // editNode(id, majoprGPA, int unused)
	
	// edit the totalCreds of a given node
	public void editTotalCreds(int id, int totalCreds){
		Node node = getNode(id);
		
		
		node.setTotalCreds(totalCreds); // change the totalCreds of the given node
		
		rewrite();
	} // editTotalCreds
	
	// edit the majorCreds of a given node
	public void editMajorCreds(int id, int majorCreds){
		Node node = getNode(id);
		
		
		node.setMajorCreds(majorCreds); // change the totalGPA of the given node
		
		rewrite();
	} // editMajorCreds
	
	// edit the upperCreds of a given node
	public void editUpperCreds(int id, int upperCreds){
		Node node = getNode(id);
		
		
		node.setUpperCreds(upperCreds); // change the totalGPA of the given node
		
		rewrite();
	} // editUpperCreds
	
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
	
	// rewrite the contents of the temp file
	private void rewrite(){
		File file = new File("temp2.txt");
		
		// write the node to the tempFile
		for(int i = 0; i < size; i++){
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(getNode(i, 0).toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: addNode"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		
		tempFile.delete();
		
		file.renameTo(tempFile);
	} // rewrite	
	
	public String toString(){
		return listAll();
	} // toString
} // class studentList

class Node{
	private String name, grade;
	private int id, totalCreds, majorCreds, upperCreds;
	private Node next; // next node in the queue
	private double totalGPA, majorGPA;
	
	// initialize this Node with default values
	public Node(){
		name = "John Doe";
		id = -1;
		grade = null;
		next = null;
		totalGPA = 0.0;
		majorGPA = 0.0;
		totalCreds = 0;
		majorCreds = 0;
		upperCreds = 0;
	} // Node constructor
	
	// initialize this Node with given values
	public Node(String name, int id, String grade){
		this.name = name;
		this.id = id;
		this.grade = grade;
		next = null;
		totalGPA = 0.0;
		majorGPA = 0.0;
		totalCreds = 0;
		majorCreds = 0;
		upperCreds = 0;
	} // Node constructor
	
	public Node getNext(){
		return next;
	} // getNext
	
	public String getName(){
		return name;
	} // getName
	
    public int getID(){
		return id;
	} // getID
    
    public String getGrade(){
		return grade;
	} // getGrade
    
	public double getTotalGPA(){
		return totalGPA;
	} // getTotalGpa
	
	public double getMajorGPA(){
		return majorGPA;
	} // getMajorGPA
	
	public int getTotalCreds(){
		return totalCreds;
	} // getTotalCreds
	
	public int getMajorCreds(){
		return majorCreds;
	} // getTotalCreds
	
	public int getUpperCreds(){
		return upperCreds;
	} // getUpperCreds
	
	public void setNext(Node next){
		this.next = next;
	} // setNext
	
    public void setName(String name){
        this.name = name;
    } // setName
    
    public void setID(int id){
        this.id = id;;
    } // setID
    
    public void setGrade(String grade){
        this.grade = grade;
    } // setGrade
	
	public void setTotalGPA(double totalGPA){
		this.totalGPA = totalGPA;
	} // setTotalGPA
	
	public void setMajorGPA(double majorGPA){
		this.majorGPA = majorGPA;
	} //setMajorGPA
	
	public void setTotalCreds(int totalCreds){
		this.totalCreds = totalCreds;
	} // setTotalCreds
	
	public void setMajorCreds(int majorCreds){
		this.majorCreds = majorCreds;
	} // setTotalCreds
	
	public void setUpperCreds(int upperCreds){
		this.upperCreds = upperCreds;
	} // setUpperCreds
	
	// return the student's information
	public String toString(){
		return "Name:" + name + " ID:" + id + " Grade:" + grade + " Total GPA:" + totalGPA + " Major GPA:" + majorGPA + " Total Credits:" + totalCreds + " Major Credits:" + majorCreds + " Upper-Level Credits:" + upperCreds;
	} // toString
} // class Node