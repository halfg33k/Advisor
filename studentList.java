import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class studentList{
	Node root;
	Node head; // where to insert new nodes
	File tempFile = new File("temp.txt"); // file to temporarily store the queue information
	File tempGrad = new File("tempGrad.txt"); // temporary storage of graduation info
	File tempAdv = new File("tempAdv.txt"); // temporary storage of advising info
	BufferedWriter writer; // to write to a file
	BufferedReader reader; // to read from a file
	int size = 0;
	
	public studentList(){
		try{
			// this file only exists while the program is running
			// think of it like RAM
			tempFile.createNewFile();
			importStudents("studentList.txt");
			importGradApps("gradApps.txt");
			importAdvising("advising.txt");
		} catch(IOException e){ System.out.println("IOException: studentList constructor"); }
	} // studentList constructor
	
	// import students from a given file
    public void importStudents(String fileName){
        File file = new File(fileName);
        Scanner scan = null;
		String name, grade, id;
        
        try{
            scan = new Scanner(file);
            scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
			
			while(scan.hasNextLine()){
				name = scan.next();
				id = scan.next();
				grade = scan.next();
				
				addNode(name, id, grade);
				
				scan.nextLine();
			}
        }catch(IOException e){ System.out.println("IOException: importStudents"); }
        catch(InputMismatchException e){ System.out.println("InputMismatchException: importStudents"); }
        catch(NoSuchElementException nse){  }
		finally{scan.close();}
    } // importStudents
	
	// import the information for students with graduation applications submitted
	public void importGradApps(String fileName){
		File file = new File(fileName);
		Scanner scan = null;
		String id, totalCreds, majorCreds, upperCreds, gradDate, totalGPA, majorGPA;
		boolean submitted;
		Node node;
		
		try{
			scan = new Scanner(file);
			scan.useDelimiter("ID:| Submitted:| Total GPA:| Major GPA:| Total Credits:| Major Credits:| Upper Credits:| Date:|\\n|\\r");
		} catch(IOException ioe){ System.out.println("IOException: importGradApps --> try"); }
			
		while(scan.hasNext()){
			try{
				id = scan.next();
				submitted = scan.nextBoolean();
				
				node = getNode(id);
				
				if(submitted){
					totalGPA = scan.next();
					majorGPA = scan.next();
					totalCreds = scan.next();
					majorCreds = scan.next();
					upperCreds = scan.next();
					gradDate = scan.next();
					
					node.setSubmitted(submitted);
					node.setTotalGPA(totalGPA);
					node.setMajorGPA(majorGPA);
					node.setTotalCreds(totalCreds);
					node.setMajorCreds(majorCreds);
					node.setUpperCreds(upperCreds);
					node.setGradDate(gradDate);
				}
				
				
				
				scan.nextLine();
			}catch(InputMismatchException ime){ System.out.println("InputMismatchException: importGradApps"); }
			catch(NoSuchElementException nse){ }
		}
		
		rewrite();
		
	} // importGradApps
	
	// import the advising information for all students
	public void importAdvising(String fileName){
		File file = new File(fileName);
		Scanner scan = null;
		String id, advDate;
		boolean advised;
		Node node;
		
		try{
			scan = new Scanner(file);
			scan.useDelimiter("ID:| Advised:| Date:|\\n|\\r");
		} catch(IOException ioe){ System.out.println("IOException: importAdvising --> try"); }
			
		while(scan.hasNext()){
			try{
				id = scan.next();
				advised = scan.nextBoolean();
				
				node = getNode(id);
				
				if(advised){
					advDate = scan.next();
					
					node.setAdvised(advised);
					node.setAdvDate(advDate);
				}
				
				scan.nextLine();
			}catch(InputMismatchException ime){ System.out.println("InputMismatchException: importAdvising"); }
			catch(NoSuchElementException nse){ }
		}
		
		rewrite();
	} // importAdvising
	
	// return the root node
	public Node getRoot(){
		return root;
	} // getRoot
	
	// create a node and add it to the studentList
	public void addNode(String name, String id, String grade){
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
		
		// write the node to tempFile
		try{
			writer = new BufferedWriter(new FileWriter(tempFile, true));
			writer.write(node.toString());
			writer.newLine();
		} catch(IOException e){ System.out.println("IOException: addNode"); }
		finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		
		// write the node to tempGrad
		try{
			writer = new BufferedWriter(new FileWriter(tempGrad, true));
			writer.write(node.getGradInfo());
			writer.newLine();
		} catch(IOException e){ System.out.println("IOException: addNode"); }
		finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		
		// write the node to tempAdv
		try{
			writer = new BufferedWriter(new FileWriter(tempAdv, true));
			writer.write(node.getGradInfo());
			writer.newLine();
		} catch(IOException e){ System.out.println("IOException: addNode"); }
		finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		
		size++;
	} // addNode(name, id, grade)
	
	// insert a given node into the studentList
	public void addNode(Node node){
		String name = node.getName();
		String id = node.getID();
		String grade = node.getGrade();
		
		addNode(name, id, grade);
	} // addNode(node)
	
	// find a particular node by it's id
	public Node getNode(String id){
		Node current = root;
		
		while(!current.getID().equals(id)){
			current = current.getNext();
			
			if(current == null)
				return null;
			else if(current.getID().equals(id))
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
	
	// remove a particular node by it's id
	public Node removeNode(String id){		
		Node current = root;
		File file = new File("temp2.txt");
		
		if(root.getID().equals(id))
			root = root.getNext();
		else{
			Node prev = root;
			try{
				while(!current.getID().equals(id)){
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
		
		size--;
		
		rewrite();
		
		return current;
	} // removeNode
			
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
		} catch(Exception e){ System.out.println("Exception: close --> studentList"); }
		finally{try{writer.close(); reader.close();}catch(Exception e){}}
		
		tempFile.delete();
		
		// now close tempGrad.txt
		file = new File("gradApps.txt");
		
		try{
			file.createNewFile();
			
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(tempGrad));
			
			String currentLine;
			
			while((currentLine = reader.readLine()) != null){
				writer.write(currentLine);
				writer.newLine();
			}
		} catch(Exception e){ System.out.println("Exception: close --> gradApps"); }
		finally{try{writer.close(); reader.close();}catch(Exception e){}}
		
		tempGrad.delete();
		
		// now close tempAdv.txt
		file = new File("advising.txt");
		
		try{
			file.createNewFile();
			
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(tempAdv));
			
			String currentLine;
			
			while((currentLine = reader.readLine()) != null){
				writer.write(currentLine);
				writer.newLine();
			}
		} catch(Exception e){ System.out.println("Exception: close --> gradApps"); }
		finally{try{writer.close(); reader.close();}catch(Exception e){}}
		
		tempAdv.delete();
	} // close
	
	// whether the list contains a given id
	public boolean contains(String id){
		for(int i = 0; i < size; i++){
			try{
				if(getNode(i, 0).getID().equals(id))
					return true;
			} catch(NullPointerException e){ System.out.println("NullPointerException: contains"); }
		}
		
		return false;
	} // contains
		
	public int getSize(){
		return size;
	} // getSize
	
	// rewrite the contents of the temp file
	public void rewrite(){
		File file = new File("temp2.txt");
		Node node;
		
		// write the node to the tempFile
		for(int i = 0; i < size; i++){
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				writer.write(getNode(i, 0).toString());
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: rewrite --> temp"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		
		tempFile.delete();
		file.renameTo(tempFile);
		
		// now rewrite tempGrad
		file = new File("tempGrad2.txt");
		
		for(int i = 0; i < size; i++){
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				node = getNode(i, 0);
				
				if(node.getSubmitted())
					writer.write(node.getGradInfo());
				else
					writer.write("ID:" + node.getID() + " Submitted:" + false);
				
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: rewrite --> tempGrad"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		
		tempGrad.delete();
		file.renameTo(tempGrad);
		
		// now rewrite tempAdv
		file = new File("tempAdv2.txt");
		
		for(int i = 0; i < size; i++){
			try{
				writer = new BufferedWriter(new FileWriter(file, true));
				node = getNode(i, 0);
				
				if(node.getAdvised())
					writer.write(node.getAdvisingInfo());
				else
					writer.write("ID:" + node.getID() + " Advised:" + false);
				
				writer.newLine();
			} catch(IOException e){ System.out.println("IOException: rewrite --> tempAdv"); }
			finally{try{ writer.close(); }catch(Exception e){}} // close the writer
		}
		
		tempAdv.delete();
		file.renameTo(tempAdv);
	} // rewrite	
	
	public String toString(){
		Node current = root;
		String studentInfo = current.toString() + "\n";
		
		while(current.getNext() != null){
			current = current.getNext();
			
			studentInfo += current.toString() + "\n";
		}
		
		return studentInfo;
	} // toString
} // class studentList

class Node{
	private String name, id, grade;
	private String totalCreds, majorCreds, upperCreds;
	private Node next; // next node in the queue
	private String totalGPA, majorGPA;
	private boolean submitted; // graduation application submitted
	private String gradDate;
	private boolean advised; // student has received academic advising
	private String advDate; // date advising last took place
	
	// initialize this Node with default values
	public Node(){
		name = "John Doe";
		//id = -1;
		grade = null;
		next = null;
		submitted = false;
	} // Node constructor
	
	// initialize this Node with given values
	public Node(String name, String id, String grade){
		this.name = name;
		this.id = id;
		this.grade = grade;
		next = null;
		submitted = false;
	} // Node constructor
	
	public Node getNext(){
		return next;
	} // getNext
	
	public String getName(){
		return name;
	} // getName
	
    public String getID(){
		return id;
	} // getID
    
    public String getGrade(){
		return grade;
	} // getGrade
    
	public String getTotalGPA(){
		return totalGPA;
	} // getTotalGpa
	
	public String getMajorGPA(){
		return majorGPA;
	} // getMajorGPA
	
	public String getTotalCreds(){
		return totalCreds;
	} // getTotalCreds
	
	public String getMajorCreds(){
		return majorCreds;
	} // getTotalCreds
	
	public String getUpperCreds(){
		return upperCreds;
	} // getUpperCreds
	
	public boolean getSubmitted(){
		return submitted;
	} // getsubmitted
	
	public String getGradDate(){
		return gradDate;
	} // getGradDate
	
	public boolean getAdvised(){
		return advised;
	} // getAdvised
	
	public String getAdvDate(){
		return advDate;
	} // getAdvDate
	
	public void setNext(Node next){
		this.next = next;
	} // setNext
	
    public void setName(String name){
        this.name = name;
    } // setName
    
    public void setID(String id){
        this.id = id;
    } // setID
    
    public void setGrade(String grade){
        this.grade = grade;
    } // setGrade
	
	public void setTotalGPA(String totalGPA){
		this.totalGPA = totalGPA;
	} // setTotalGPA
	
	public void setMajorGPA(String majorGPA){
		this.majorGPA = majorGPA;
	} //setMajorGPA
	
	public void setTotalCreds(String totalCreds){
		this.totalCreds = totalCreds;
	} // setTotalCreds
	
	public void setMajorCreds(String majorCreds){
		this.majorCreds = majorCreds;
	} // setTotalCreds
	
	public void setUpperCreds(String upperCreds){
		this.upperCreds = upperCreds;
	} // setUpperCreds
	
	public void setSubmitted(boolean submitted){
		this.submitted = submitted;
	} // set submitted
	
	public void setGradDate(String gradDate){
		this.gradDate = gradDate;
	} // setGradDate
	
	public void setAdvised(boolean advised){
		this.advised = advised;
	} // setAdvised
	
	public void setAdvDate(String advDate){
		this.advDate = advDate;
	} // setAdvDate
	
	// return the student's advising information
	public String getAdvisingInfo(){
		if(advised)
			return "ID:" + id + " Advised:" + advised + " Date:" + advDate;
		else
			return "ID:" + id + "Advised:" + advised;
	} // getAdvisingInfo
	
	// return the student's graduation application information
	public String getGradInfo(){
		if(submitted)
			return "ID:" + id + " Submitted:" + submitted + " Total GPA:" + totalGPA + " Major GPA:" + majorGPA 
				+ " Total Credits:" + totalCreds + " Major Credits:" + majorCreds + " Upper Credits:" + upperCreds 
				+ " Date:" + gradDate;
		else
			return "ID:" + id + " Submitted:" + submitted;
	} // getGradInfo
	
	// return the student's information
	public String toString(){
		return "Name:" + name + " ID:" + id + " Grade:" + grade;
	} // toString
} // class Node