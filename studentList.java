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
	
	public studentList(){
		try{
			// this file only exists while the program is running
			// think of it like RAM
			tempFile.createNewFile();
		} catch(IOException e){ System.out.println("IOException: studentList constructor"); }
	} // studentList constructor
	
	// import students from a given file
    public void importStudents(String fileName){
        File studFile = new File(fileName);
        Scanner scan;
        
        /*if(!studFile.isFile()){
            try{
                studFile.createNewFile();
            } catch(Exception e){ System.out.println("Exception: importStudents"); }
        }*/
        
        try{
            scan = new Scanner(studFile);
            scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
            
            while(scan.hasNextLine()){
                addNode(scan.next(), scan.nextInt(), scan.nextInt());
                //System.out.println(scan.next() + " " + scan.nextInt() + " " + scan.nextInt());
                scan.nextLine();
            }
        }catch(IOException e){ System.out.println("IOException: importStudents"); }
         catch(InputMismatchException e){ System.out.println("InputMismatchException: importStudents"); }
         catch(NoSuchElementException e){}
    } // importStudents
	
	// add a node to the studentList
	public void addNode(String name, int id, int grade){
		Node node = new Node(name, id, grade);
		
		if (root == null){
			root = node;
			head = root;
			
			try{
				writer = new BufferedWriter(new FileWriter(tempFile, true));
				writer.write(node.toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: addNode 1"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		else{
			head.setNext(node);
			head = node;
			
			try{
				writer = new BufferedWriter(new FileWriter(tempFile, true));
				writer.write(node.toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: addNode 2"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
	} // addNode(info)
	
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
	
	// edit the name of a given node
	public void editNode(int id, String name){
		Node node = getNode(id);
		
		node.setName(name);
	} // editNode
	
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
		
	// delete the temp file and transfer its info to studentList.txt
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
    
    public int getGrade(){
		return grade;
	} // getGrade
    
    public void setGrade(int Grade){
        this.grade = grade;
    } // setGrade
	
	// return the student's information
	public String toString(){
		return "Name:" + name + " ID:" + id + " Grade:" + grade;
	} // toString
} // class Node