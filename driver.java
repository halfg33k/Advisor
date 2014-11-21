public class driver{
	public static void main(String[] args){
		studentList studs = new studentList();
		
		studs.editTotalCreds(25, 78);
		studs.editMajorCreds(25, 20);
		studs.editUpperCreds(25, 18);
		
		studs.close();
	}
}