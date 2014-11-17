import java.util.Scanner;
import java.util.InputMismatchException;

public class driver{
    public static void main(String[] args){
        studentList list = new studentList();
		
		list.importStudents("studentList.txt");
		
		//list.importStudents("newStudents.txt");
		
		list.editNode(20, "Mark Williams");
		
		//list.removeNode(20);
		
		System.out.println(list);
		
		list.close();
    } // main
} // class driver