public class driver{
    public static void main(String[] args){
        studentList list = new studentList();

        //list.addNode("Patrick", 24, 1);
        //list.addNode("Sandy", 18, 0);
        //list.addNode("Mr. Krabs", 24, 3);

		list.importStudents("studentList.txt");

        //list.importStudents("newStudents.txt");
        
        System.out.println("\n" + list);
		
		list.close();
    } // main
} // class driver