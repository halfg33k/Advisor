public class driver{
	public static void main(String[] args){
		studentList studs = new studentList();
		
		System.out.println(studs);
		studs.editNode(54, "Allen");
		System.out.println("========================\n");
		
		System.out.println(studs);
		
		System.out.println("========================\n");
		
		for(int i = 0; i < studs.getSize(); i++){
			System.out.println(studs.getNode(i, 0).toString());
		}
		
		//studs.close();
	}
}