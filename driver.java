public class driver{
    public static void main(String[] args){
        studentList list = new studentList();

		list.importStudents("studentList.txt");

        //list.importStudents("newStudents.txt");
        
        //System.out.println("\n" + list);
		
		list.close();
    } // main
} // class driver