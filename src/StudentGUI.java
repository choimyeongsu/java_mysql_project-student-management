import java.awt.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Pattern;


public class StudentGUI extends JFrame {
    private JTextField DBusername, Idx, Score, Id, Password, Sex, pfDBPassword;
    private JButton btnLogin, btnInsert, btnSelect, btnDelete, btnCorrect;
    private String dbUsername, dbPassword; 
    private JScrollPane scrollPane;
    private JTable table;
    
    private boolean isLogin = false; 
    
    public StudentGUI() {
        Frame();
    }
    
    public void Frame() {
        setTitle("IT정보공학과 JAVA 객체지향프로그래밍 학생 관리 프로그램");
        setBounds(500, 300, 1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //데이터베이스입력 패널
        JPanel pNorth = new JPanel();
        pNorth.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        getContentPane().add(pNorth, BorderLayout.NORTH);
        pNorth.setLayout(new GridLayout(0, 4, 0, 0));
       
         
        //데이터베이스 계정입력 패널
        JPanel pDBUsername = new JPanel();
        pNorth.add(pDBUsername);
        
        pDBUsername.add(new JLabel("이름 : "));
        
        DBusername = new JTextField(); 		
        pDBUsername.add(DBusername);
        DBusername.setColumns(10);

        
        //데이터베이스 암호입력 패널
        JPanel pDBPassword = new JPanel();
        pNorth.add(pDBPassword);
        
        pDBPassword.add(new JLabel("비밀번호 : "));

        pfDBPassword = new JTextField();
        pDBPassword.add(pfDBPassword);
        pfDBPassword.setColumns(10);
        
        
        btnLogin = new JButton("로그인");
        pNorth.add(btnLogin);
        
        //회원가입정보 입력 패널
        JPanel INFO = new JPanel();
        INFO.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        getContentPane().add(INFO, BorderLayout.EAST);
        INFO.setLayout(new GridLayout(5, 0, 0, 0));

        
        //학번입력패널
        JPanel pIdx = new JPanel();
        FlowLayout flowLayout = (FlowLayout) pIdx.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        INFO.add(pIdx);
        
        pIdx.add(new JLabel("학 번"));
        
        Idx = new JTextField();
        pIdx.add(Idx);
        Idx.setColumns(10);
        
        
        //학점입력패널
        JPanel pScore = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) pScore.getLayout();
        flowLayout_1.setAlignment(FlowLayout.RIGHT);
        INFO.add(pScore);
        
        pScore.add(new JLabel("학 점"));
        
        Score = new JTextField();
        pScore.add(Score);
        Score.setColumns(10);
        
        
        //이름입력패널
        JPanel pId = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) pId.getLayout();
        flowLayout_2.setAlignment(FlowLayout.RIGHT);
        INFO.add(pId);
        
        pId.add(new JLabel("이 름"));
        
        Id = new JTextField();
        pId.add(Id);
        Id.setColumns(10);

        
        //비밀번호입력패널
        JPanel pPassword = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) pPassword.getLayout();
        flowLayout_3.setAlignment(FlowLayout.RIGHT);
        INFO.add(pPassword);
        
        pPassword.add(new JLabel("비밀번호"));
        
        Password = new JTextField();
        pPassword.add(Password);
        Password.setColumns(10);
        
        
        //성별입력패널
        JPanel pSex = new JPanel();
        FlowLayout flowLayout_4 = (FlowLayout) pSex.getLayout();
        flowLayout_4.setAlignment(FlowLayout.RIGHT);
        INFO.add(pSex);
        
        pSex.add(new JLabel("성 별"));
        
        Sex = new JTextField();
        pSex.add(Sex);
        Sex.setColumns(10);
        
        

        
        //학생정보추가,삭제,목록,수정 버튼 패널
        JPanel pSouth = new JPanel();
        pSouth.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        getContentPane().add(pSouth, BorderLayout.SOUTH);
        
        btnInsert = new JButton("학생정보 추가");
        pSouth.add(btnInsert);
               
        btnCorrect = new JButton("학생정보 수정");
        pSouth.add(btnCorrect);
        
        
        btnDelete = new JButton("학생정보 삭제");
        pSouth.add(btnDelete);
        
        btnSelect = new JButton("학생 목록");
        pSouth.add(btnSelect);
        
        
        scrollPane = new JScrollPane();
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        table = new JTable();
        table.getTableHeader().setReorderingAllowed(false); // 셀 고정
        table.getTableHeader().setResizingAllowed(false); // 컬럼 사이즈 고정
        scrollPane.setViewportView(table);
        
        //테이블컬럼명설정
        Vector<String> columnNames = new Vector<String>(
              Arrays.asList("학 번", "학 점", "이 름", "비밀번호", "성 별")); 
         // 컬럼명 추가
        DefaultTableModel dtm = new DefaultTableModel(columnNames, 0) {

         
         public boolean isCellEditable(int row, int column) {
            return false;
         }
           
        };
        //모델 객체테이블에 추가
        table.setModel(dtm);        
        setVisible(true);
        
        //이벤트처리
        btnLogin.addActionListener(listener);
        btnInsert.addActionListener(listener);
        btnDelete.addActionListener(listener);
        btnSelect.addActionListener(listener);
        btnCorrect.addActionListener(listener);
        table.addMouseListener(new MouseAdapter() {
         
         public void mouseClicked(MouseEvent e) {
            showCustomerInfo();
         }
         
      });
    }
    
    public void showCustomerInfo() {
       Idx.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
       Score.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
       Id.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
       Password.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
       Sex.setText(table.getValueAt(table.getSelectedRow(), 4).toString());
       
   }

   ActionListener listener = new ActionListener() {      
      
         public void actionPerformed(ActionEvent e) {
         if(e.getSource() == btnLogin) {
            login();
         } else if(e.getSource() == btnInsert) {
            insert();
         } else if(e.getSource() == btnDelete) {
            delete();
         } else if(e.getSource() == btnSelect) {
            select();
         } else if(e.getSource()== btnCorrect) {
        	correct();
         }
          
      }
   };
   
   //데이터베이스로그인기능
   public void login() {
      //로그인되어있을경우
      if(isLogin==true)
      {         
         btnLogin.setText("로그인");
         DBusername.setEditable(true);
         pfDBPassword.setEditable(true);
         DBusername.setText("");
         pfDBPassword.setText("");
         isLogin = false;
         JOptionPane.showMessageDialog(this, "로그아웃 성공!");
         
      }
      else if(isLogin==false)
      {         
         String dbUsername = DBusername.getText();
         String dbPassword = new String(pfDBPassword.getText());
         
            if(dbUsername.length() <= 0) {
              JOptionPane.showMessageDialog(this, "이름을 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);              
              return;
           } else if(dbPassword.length() <= 0) {
              JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);             
              return;
           } 
        
            StudentDB dao = StudentDB.getcto();
            try {
            boolean result = dao.login(dbUsername, dbPassword);            
            // 로그인 성공-> 메세지출력 하고 잠금설정 -> 버튼 텍스트 변경
            JOptionPane.showMessageDialog(this, "로그인하였습니다.");
            DBusername.setEditable(false);
            pfDBPassword.setEditable(false);
            btnLogin.setText("로그아웃");
            isLogin = result;
         } catch (LoginFailException e) {
            JOptionPane.showMessageDialog(this, "로그인 실패 : ","오류", JOptionPane.ERROR_MESSAGE);
         }
      }
      
      
      
   }
   
   public void select() {
      if(isLogin==false) {
         JOptionPane.showMessageDialog(this, "로그인을 해주세요.", "경고", JOptionPane.ERROR_MESSAGE);      
         return;
      }
      StudentDB dao = StudentDB.getcto();
      Vector<Vector> data = dao.select();
      DefaultTableModel dtm = (DefaultTableModel)table.getModel();
      dtm.setRowCount(0);      
      for(Vector tempdata : data) {
         dtm.addRow(tempdata);
      }
      
      
   }
   public void correct()
   {
	   if(isLogin==false) {
	         JOptionPane.showMessageDialog(this, "로그인을 해주세요.", "경고", JOptionPane.ERROR_MESSAGE);      
	         return;
	   }
	   //regex -> 숫자만허용
	   String temp = "^[0-9]{1,}$";
	   String inputData = JOptionPane.showInputDialog(this, "수정할 회원 학번을 입력하세요!");
	   
	   //inputData가 숫자이면 true가 된다
	   if(Pattern.matches(temp, inputData)==false){
	         JOptionPane.showMessageDialog(this, "학번만 입력해주세요", "입력 오류", JOptionPane.ERROR_MESSAGE);
	         return;
	      }
	   
	 
	      //String idx =tfIdx.getText();
	      String score = Score.getText();
	      String id = Id.getText();
	      String password = Password.getText();
	      String sex = Sex.getText();
	      
	      StudentDB dao = StudentDB.getcto();
	      boolean result = dao.correct(inputData, score, id, password, sex);
	      
	        if(result==true) {
	           JOptionPane.showMessageDialog(this, "수정하였습니다.");
	           select();
	        } else if(result==false){
	           JOptionPane.showMessageDialog(this, "수정에 실패하였습니다.");
	        }
   }

   public void delete() {
      if(isLogin==false) {
         JOptionPane.showMessageDialog(this, "로그인을 해주세요.", "경고", JOptionPane.ERROR_MESSAGE);      
         return;
      }
      //temp -> 숫자만허용하는
      String temp = "^[0-9]{1,}$";
      String inputData = JOptionPane.showInputDialog(this, "삭제할 회원 학번을 입력하세요!");
      
      //inputData가 숫자이면 true가 된다.
      if(Pattern.matches(temp, inputData)==false){
         JOptionPane.showMessageDialog(this, "번호를 입력해주세요", "입력 오류", JOptionPane.ERROR_MESSAGE);
         return;
      }      
      
      StudentDB dao = StudentDB.getcto();
      boolean result = dao.delete(inputData);
      
        if(result==true) {
           JOptionPane.showMessageDialog(this, "삭제하였습니다.");
           select();
        } else if(result==false) {
           JOptionPane.showMessageDialog(this, "삭제에 실패하였습니다.");
        }
      
   }
   // 회원추가 버튼 클릭 시 호출되는 메서드
   public void insert() { 
      // 로그인 상태가 아니면메서드종료
      if(isLogin==false) { 
         JOptionPane.showMessageDialog(this, "로그인을 해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
         return;
      }
      String idx = Idx.getText();
      String score = Score.getText();
      String id = Id.getText();
      String password = Password.getText();
      String sex = Sex.getText();
      
        if(Idx.getText().length() <= 0)
        {
           JOptionPane.showMessageDialog(this, "학번을 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);            
            return;
        }
        else if(Score.getText().length() <= 0) {
           JOptionPane.showMessageDialog(this, "학점을 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);            
            return;
        } else if(Id.getText().length() <= 0) {
           JOptionPane.showMessageDialog(this, "이름을 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);           
           return;
        } else if(Password.getText().length() <= 0) {
           JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);           
            return;
        } else if(Sex.getText().length() <= 0) {
           JOptionPane.showMessageDialog(this, "성별을 입력하세요.", "실패", JOptionPane.ERROR_MESSAGE);           
           return;
        }
        
        StudentDB dao = StudentDB.getcto();
        boolean result = dao.insert(idx, score, id, password, sex);
        
        if(result==true) {
           JOptionPane.showMessageDialog(this, "회원을 추가하였습니다.");
           select();
        } else if(result==false) {
           JOptionPane.showMessageDialog(this, "회원추가에 실패하였습니다.");
        }
        
        
   }
    public static void main(String[] args) {
      new StudentGUI();
   }

}