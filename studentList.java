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
		String name, grade;
		int id;
        
        try{
            scan = new Scanner(file);
            scan.useDelimiter("Name:| ID:| Grade:|\\n|\\r");
			
			while(scan.hasNextLine()){
				name = scan.next();
				id = scan.nextInt();
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
		int id, totalCreds, majorCreds, upperCreds, month, day, year;
		double totalGPA, majorGPA;
		boolean submitted;
		Node node;
		
		try{
			scan = new Scanner(file);
			scan.useDelimiter("ID:| Submitted:| Total GPA:| Major GPA:| Total Credits:| Major Credits:| Upper Credits:| Date:|/|\\n|\\r");
			
			while(scan.hasNext()){
				id = scan.nextInt();
				submitted = scan.nextBoolean();
				
				node = getNode(id);
				
				if(submitted){
					totalGPA = scan.nextDouble();
					majorGPA = scan.nextDouble();
					totalCreds = scan.nextInt();
					majorCreds = scan.nextInt();
					upperCreds = scan.nextInt();
					month = scan.nextInt();
					day = scan.nextInt();
					year = scan.nextInt();
					
					node.setSubmitted(submitted);
					node.setTotalGPA(totalGPA);
					node.setMajorGPA(majorGPA);
					node.setTotalCreds(totalCreds);
					node.setMajorCreds(majorCreds);
					node.setUpperCreds(upperCreds);
					node.setGradMonth(month);
					node.setGradDay(day);
					node.setGradYear(year);
				}
				
				// write the graduation info to tempGrad.txt
				try{
					writer = new BufferedWriter(new FileWriter(tempGrad, true));
					
					if(submitted)
						writer.write(node.getGradInfo());
					else
						writer.write("ID:" + node.getID() + " Submitted:" + false);
					
					writer.newLine();
				} catch(IOException e){ System.out.println("IOException: importGradApps --> file writing"); }
				finally{try{ writer.close(); }catch(Exception e){}} // close the writer
				
				scan.nextLine();
			}
		} catch(IOException ioe){ System.out.println("IOException: importGradApps --> try"); }
		catch(InputMismatchException ime){ System.out.println("InputMismatchException: importGradApps"); }
		catch(NoSuchElementException nse){ }
		finally{scan.close();}
	} // importGradApps
	
	// import the advising information for all students
	public void importAdvising(String fileName){
		File file = new File(fileName);
		Scanner scan = null;
		int id, month, day, year;
		boolean advised;
		Node node;
		
		try{
			scan = new Scanner(file);
			scan.useDelimiter("ID:| Advised:| Date:|/|\\n|\\r");
			
			while(scan.hasNext()){
				id = scan.nextInt();
				advised = scan.nextBoolean();
				
				node = getNode(id);
				
				if(advised){
					month = scan.nextInt();
					day = scan.nextInt();
					year = scan.nextInt();
					
					node.setAdvised(advised);
					node.setAdvMonth(month);
					node.setAdvDay(day);
					node.setAdvYear(year);
				}
				
				// write the graduation info to tempAdv.txt
				try{
					writer = new BufferedWriter(new FileWriter(tempAdv, true));
					
					if(advised)
						writer.write(node.getAdvisingInfo());
					else
						writer.write("ID:" + node.getID() + " Advised:" + false);
					
					writer.newLine();
				} catch(IOException e){ System.out.println("IOException: importAdvising--> file writing"); }
				finally{try{ writer.close(); }catch(Exception e){}} // close the writer
				
				scan.nextLine();
			}
		} catch(IOException ioe){ System.out.println("IOException: importAdvising --> try"); }
		catch(InputMismatchException ime){ System.out.println("InputMismatchException: importAdvising"); }
		catch(NoSuchElementException nse){ }
		finally{scan.close();}
	} // importAdvising
	
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
	} // addNode(name, id, grade, totalGPA, majorGPA, totalCreds, majorCreds, upperCreds)
	
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
		getNode(id).setName(name);
		
		rewrite();
	} // editNode(id, name)
	
	// edit the id of a given node
	public void editID(int id, int newID){
		getNode(id).setID(newID); // change the id of the given node
		
		rewrite();
	}
	
	// edit the grade of a given node
	public void editGrade(int id, String grade){
		getNode(id).setGrade(grade);
		
		rewrite();
	} // editNode(id, grade)
	
	// edit the totalGPA of a given node
	public void editTotalGPA(int id, double totalGPA){
		getNode(id).setTotalGPA(totalGPA); // change the totalGPA of the given node
		
		rewrite();
	} // editNode(id, totalGPA)
	
	// edit the majorGPA of a given node
	public void editMajorGPA(int id, double majorGPA){
		getNode(id).setMajorGPA(majorGPA); // change the totalGPA of the given node
		
		rewrite();
	} // editNode(id, majoprGPA, int unused)
	
	// edit the totalCreds of a given node
	public void editTotalCreds(int id, int totalCreds){
		getNode(id).setTotalCreds(totalCreds); // change the totalCreds of the given node
		
		rewrite();
	} // editTotalCreds
	
	// edit the majorCreds of a given node
	public void editMajorCreds(int id, int majorCreds){
		getNode(id).setMajorCreds(majorCreds); // change the totalGPA of the given node
		
		rewrite();
	} // editMajorCreds
	
	// edit the upperCreds of a given node
	public void editUpperCreds(int id, int upperCreds){
		getNode(id).setUpperCreds(upperCreds); // change the totalGPA of the given node
		
		rewrite();
	} // editUpperCreds
	
	// edit whether a graduation application has been submitted
	public void editSubmitted(int id, boolean submitted){
		getNode(id).setSubmitted(submitted);
		
		rewrite();
	} // editSubmitted
	
	// edit the date of submission for a student's graduation application
	public void editGradDate(int id, int month, int day, int year){
		getNode(id).setGradMonth(month);
		getNode(id).setGradDay(day);
		getNode(id).setGradYear(year);
		
		rewrite();
	} // editGradDate
	
	// edit whether the student has been advised
	public void editAdvised(int id, boolean advised){
		getNode(id).setAdvised(advised);
		
		rewrite();
	} // editAdvised
	
	// edit the date of advising for the given student
	public void editAdvDate(int id, int month, int day, int year){
		getNode(id).setAdvMonth(month);
		getNode(id).setAdvDay(day);
		getNode(id).setAdvYear(year);
		
		rewrite();
	} // editAdvDate
	
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
		return listAll();
	} // toString
} // class studentList

class Node{
	private String name, grade;
	private int id, totalCreds, majorCreds, upperCreds;
	private Node next; // next node in the queue
	private double totalGPA, majorGPA;
	private boolean submitted; // graduation application submitted
	private int gradMonth, gradDay, gradYear; // date graduation application was submitted
	private boolean advised; // student has received academic advising
	private int advMonth, advDay, advYear; // date advising last took place
	
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
		submitted = false;
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
		submitted = false;
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
	
	public boolean getSubmitted(){
		return submitted;
	} // getsubmitted
	
	public int getGradMonth(){
		return gradMonth;
	} // getGradMonth
	
	public int getGradDay(){
		return gradDay;
	} // getGradDay
	
	public int getGradYear(){
		return gradYear;
	} // getGradYear
	
	public boolean getAdvised(){
		return advised;
	} // getAdvised
	
	public int getAdvMonth(){
		return advMonth;
	} // getAdvMonth
	
	public int getAdvDay(){
		return advDay;
	} // getAdvDay
	
	public int getAdvYear(){
		return advYear;
	} // getAdvYear
	
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
	
	public void setSubmitted(boolean submitted){
		this.submitted = submitted;
	} // setsubmitted
	
	public void setGradMonth(int gradMonth){
		this.gradMonth = gradMonth;
	} // setGradMonth
	
	public void setGradDay(int gradDay){
		this.gradDay = gradDay;
	} // setGradDay
	
	public void setGradYear(int gradYear){
		this.gradYear = gradYear;
	} // setGradYear
	
	public void setAdvised(boolean advised){
		this.advised = advised;
	} // setAdvised
	
	public void setAdvMonth(int advMonth){
		this.advMonth = advMonth;
	} // setAdvMonth
	
	public void setAdvDay(int advDay){
		this.advDay = advDay;
	} // setAdvDay
	
	public void setAdvYear(int advYear){
		this.advYear = advYear;
	} // setAdvYear
	
	// return the student's advising information
	public String getAdvisingInfo(){
		return "ID:" + id + " Advised:" + advised + " Date:" + advMonth + "/" + advDay + "/" + advYear;
	} // getAdvisingInfo
	
	// return the student's graduation application information
	public String getGradInfo(){
		return "ID:" + id + " Submitted:" + submitted + " Total GPA:" + totalGPA + " Major GPA:" + majorGPA 
			+ " Total Credits:" + totalCreds + " Major Credits:" + majorCreds + " Upper Credits:" + upperCreds 
			+ " Date:" + gradMonth + "/" + gradDay + "/" + gradYear;
	} // getGradInfo
	
	// return the student's information
	public String toString(){
		return "Name:" + name + " ID:" + id + " Grade:" + grade;
	} // toString
} // class Node