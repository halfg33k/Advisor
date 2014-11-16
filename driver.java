public class driver{
	public static void main(String[] args){
		studentList list = new studentList();
		
		list.addNode("Patrick", 24, 1);
		list.addNode("Kate", 18, 0);
		list.addNode("Daniel", 24, 3);
		
		System.out.println(list);
	} // main
} // class driver