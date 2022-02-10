import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

public class StudentDB {
   
   PreparedStatement pstmt = null; 
   ResultSet rs = null; // ��� ������ ���� �� �ִ� ��ü
   
   
   public StudentDB() {}
   
   public static StudentDB cto = new StudentDB();

   public  static  StudentDB getcto() {
      return cto;
   }
   //�����ͺ��̽��� �������ִ� �ӽð�ü
   Connection con = null;    
   String temp;
   int indextemp;
   //�����ͺ��̽������۾����� �� ���ᰴü con�� �����ϴ� �޼���
   public Connection getConnection(String temp) {
      String driver = "com.mysql.cj.jdbc.Driver";
      String url = "";
      String user = "";
      String password = "";
      
      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, user, password);
         System.out.println("MYSQL ���� ���� ����");
      } catch (ClassNotFoundException e) {
    	  System.out.println("MYSQL ���� ���� ����" + e.toString());
      } catch (SQLException e) {
    	  System.out.println("MYSQL ���� ���� ����" + e.toString());
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
   
   //��������, ������ȣ�� ���޹޾� �α��� ����� ����
   public boolean login(String dbUsername, String dbPassword) throws LoginFailException {
      // getConnection() �޼��带 ȣ���Ͽ� Connection ��ü ��������
      con = getConnection(dbUsername);
            
      boolean result = false; // �α��� ��� ó�� false�� ���� 
      
      try {
         String sql = "SELECT password FROM customer WHERE id=?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, dbUsername);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            if(dbPassword.equals(rs.getString(1))) {
               result = true; //�α��� �� ��� true
            } else {
               throw new LoginFailException("�н����� Ʋ��");
            }
         } else {
            throw new LoginFailException("�������� �ʴ� ��������");
         }
      } catch (SQLException e) {
    	  e.toString();
      } finally {
         closeDb();
      }
      
      return result;
   }
    //ȸ������ ����
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
    //ȸ������ ����
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