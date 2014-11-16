public class driver{
    public static void main(String[] args){
        studentList list = new studentList();

        list.addNode("Patrick", 24, 1);
        list.addNode("Sandy", 18, 0);
        list.addNode("Mr. Krabs", 24, 3);

        System.out.println(list);

        list.importStudents("newStudents.txt");
    } // main
} // class driver