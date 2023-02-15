package dao;

public class AdminDAO {

	private static AdminDAO instance = null;
	private AdminDAO() {}
	public static AdminDAO getInstance() {
		if(instance == null) instance = new AdminDAO();
		
		return instance;
	}
	
	
}
