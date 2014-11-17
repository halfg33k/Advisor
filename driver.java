import java.util.Scanner;
import java.util.InputMismatchException;

public class driver{
    public static void main(String[] args){
        studentList list = new studentList();
		
		list.importStudents("studentList.txt");
		
		//list.importStudents("newStudents.txt");
		
		list.close();
    } // main
} // class driver