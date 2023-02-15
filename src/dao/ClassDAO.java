package dao;

import java.util.List;
import java.util.Map;

import util.JDBCUtil;

//Class 테이블 : CLASS_CODE, CLASS_TITLE, CLASS_TRAINER, PET_TYPE, CLASS_CONTENT, CLASS_DATE
//Register 테이블 : REGISTER_CODE, MEM_ID, CLASS_CODE

public class ClassDAO {
	private static ClassDAO instance = null;
	private ClassDAO() {}
	public static ClassDAO getInstance() {
		if(instance == null) instance = new ClassDAO();
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	
	public List<Map<String, Object>> list () {
		return jdbc.selectList("SELECT * FROM CLASS ORDER BY CLASS_NUM ASC ");
	}
	
	public Map<String, Object> detail (List<Object> param) {
		return jdbc.selectOne(" SELECT * FROM CLASS WHERE CLASS_NUM = ? ",param);
	}
	
	public List<Map<String, Object>> check (List<Object> param) {
		return jdbc.selectList(" SELECT * FROM REGISTER WHERE CLASS_CODE = (SELECT CLASS_CODE FROM CLASS WHERE CLASS_NUM = ?)  AND MEM_ID = ?",param);
	}
	
	public int rsv (List<Object> param) {
		return jdbc.update(" INSERT INTO REGISTER(REGISTER_CODE, CLASS_CODE, MEM_ID) "
    						+ " VALUES (REG_SEQ.NEXTVAL,(SELECT CLASS_CODE FROM CLASS WHERE CLASS_NUM = ?),?) ",param);
	}
	
	public List<Map<String, Object>> rsvlist (List<Object> param) {
		return jdbc.selectList(" SELECT * " + 
							   "   FROM CLASS A,REGISTER B " + 
							   "  WHERE A.CLASS_CODE=B.CLASS_CODE " +
							   "    AND B.MEM_ID = ? ",param);
	}
	
	public int delete (List<Object> param) {
		return jdbc.update(" DELETE REGISTER WHERE MEM_ID = ? AND CLASS_CODE = (SELECT CLASS_CODE FROM CLASS WHERE CLASS_NUM = ?) ",param);
	}

}
