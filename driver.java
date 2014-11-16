public class driver{
	public static void main(String[] args){
		studentList list = new studentList();
		
		list.addStudent("Patrick", 24, 1);
		list.addStudent("Kate", 18, 0);
		list.addStudent("Daniel", 24, 3);
		
		System.out.println(list);
		System.out.println(list.getNode("Lionel"));
	} // main
} // class driver