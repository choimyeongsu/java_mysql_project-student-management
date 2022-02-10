import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class StudentDB {
   
   PreparedStatement pstmt = null; 
   ResultSet rs = null; // 어떠한 정보를 담을 수 있는 객체
   
   
   public StudentDB() {}
   
   public static StudentDB cto = new StudentDB();

   public  static  StudentDB getcto() {
      return cto;
   }
   //데이터베이스에 접근해주는 임시객체
   Connection con = null;    
   String temp;
   int indextemp;
   //데이터베이스연결작업수행 후 연결객체 con을 리턴하는 메서드
   public Connection getConnection(String temp) {
      String driver = "com.mysql.cj.jdbc.Driver";
      String url = "";
      String user = "";
      String password = "";
      
      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, user, password);
         System.out.println("MYSQL 서버 연동 성공");
      } catch (ClassNotFoundException e) {
    	  System.out.println("MYSQL 서버 연동 실패" + e.toString());
      } catch (SQLException e) {
    	  System.out.println("MYSQL 서버 연동 실패" + e.toString());
      } 
      return con;
   }
   
   public void closeDb() {
	   
      try { rs.close(); 
      } catch (Exception e) {}
      try { pstmt.close();
      } catch (Exception e) {}
      try { con.close();
      } catch (Exception e) {}
   }
   
   //유저네임, 유저암호를 전달받아 로그인 기능을 수행
   public boolean login(String dbUsername, String dbPassword) throws LoginFailException {
      // getConnection() 메서드를 호출하여 Connection 객체 가져오기
      con = getConnection(dbUsername);
            
      boolean result = false; // 로그인 결과 처음 false로 설정 
      
      try {
         String sql = "SELECT password FROM customer WHERE id=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, dbUsername);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            if(dbPassword.equals(rs.getString(1))) {
               result = true; //로그인 된 경우 true
            } else {
               throw new LoginFailException("패스워드 틀림");
            }
         } else {
            throw new LoginFailException("존재하지 않는 유저네임");
         }
      } catch (SQLException e) {
    	  e.toString();
      } finally {
         closeDb();
      }
      
      return result;
   }
    //회원정보 삽입
   public boolean insert(String idx, String score, String id, String password, String sex) {
      boolean result = false;
      con = getConnection(temp);
      try {
         String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, idx);
         pstmt.setString(2, score);
         pstmt.setString(3, id);
         pstmt.setString(4, password);
         pstmt.setString(5, sex);
         pstmt.executeUpdate();
         result = true;
      } catch (SQLException e) {
    	  e.toString();
      }  finally {
         closeDb();
      }
      
      return result;
   }
   
   public boolean correct(String inputData, String score, String id, String password, String sex)
   {
	   boolean result = false;
	   con=getConnection(temp);
	   int index = Integer.parseInt(inputData);
	   System.out.println(index);
	   try {
		   String sql = "UPDATE customer SET idx=?, score=?, id=?, password=?, sex=? WHERE idx=?";
		   pstmt = con.prepareStatement(sql);	
		   pstmt.setString(1, inputData);
	       pstmt.setString(2, score);
	       pstmt.setString(3, id);
	       pstmt.setString(4, password);
	       pstmt.setString(5, sex);
	       pstmt.setString(6, inputData);
	       pstmt.executeUpdate();
	       result = true;
	   }catch (SQLException e) {
	    	  e.toString();
	   }  finally {
	         closeDb();
	   }
	      
	   return result;
   }
    //회원정보 삭제
   public boolean delete(String inputData) {
      
      boolean result = false;
      con = getConnection(temp);      
      try {
         String sql = "DELETE FROM customer WHERE idx=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, inputData);
         pstmt.executeUpdate();
         result = true;
      } catch (SQLException e) {
    	  e.toString();
      } finally {
         closeDb();
      }
      return result;
   }
    
   public Vector<Vector> select() {
      Vector<Vector> vector = new Vector<Vector>();
      con = getConnection(temp);
      try {
         String sql = "SELECT * FROM customer";
         pstmt = con.prepareStatement(sql);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            Vector<String> obj = new Vector<String>();
            obj.addElement(rs.getString("idx"));
            obj.addElement(rs.getString("score"));
            obj.addElement(rs.getString("id"));
            obj.addElement(rs.getString("password"));
            obj.addElement(rs.getString("sex"));
            vector.addElement(obj);
         }
      } catch (SQLException e) {
    	  e.toString();
      } finally {
         closeDb();
      }
      
      return vector;
   }
   
}