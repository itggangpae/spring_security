package kr.co.adamsoft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
  "file:src/main/webapp/WEB-INF/spring/root-context.xml",
  "file:src/main/webapp/WEB-INF/spring/security-context.xml"
  })

public class MemberTests {

  @Autowired
  private PasswordEncoder pwencoder;
  
  @Autowired
  private DataSource ds;
  
  //@Test
  public void testInsertMember() {

    String sql = "insert into tbl_member(userid, userpw, username) values (?,?,?)";
    
    for(int i = 0; i < 100; i++) {
      Connection con = null;
      PreparedStatement pstmt = null;
      
      try {
        con = ds.getConnection();
        pstmt = con.prepareStatement(sql);
        
        pstmt.setString(2, pwencoder.encode("pw" + i));
        System.out.println(pwencoder.encode("pw" + i));
        if(i <80) {
          pstmt.setString(1, "user"+i);
          pstmt.setString(3,"일반사용자"+i);
        }else if (i <90) {
          pstmt.setString(1, "manager"+i);
          pstmt.setString(3,"운영자"+i);
        }else {
          pstmt.setString(1, "admin"+i);
          pstmt.setString(3,"관리자"+i);
        }
        
        pstmt.executeUpdate();
        
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
        if(con != null) { try { con.close();  } catch(Exception e) {} }
        
      }
    }//end for
  }
  
  @Test
  public void testInsertAuth() {
    String sql = "insert into tbl_member_auth (userid, auth) values (?,?)";
    
    for(int i = 0; i < 100; i++) {
      
      Connection con = null;
      PreparedStatement pstmt = null;
      
      try {
        con = ds.getConnection();
        pstmt = con.prepareStatement(sql);
      
        if(i <80) {
          pstmt.setString(1, "user"+i);
          pstmt.setString(2,"ROLE_USER");
        }else if (i <90) {
          pstmt.setString(1, "manager"+i);
          pstmt.setString(2,"ROLE_MEMBER");
        }else {
          pstmt.setString(1, "admin"+i);
          pstmt.setString(2,"ROLE_ADMIN");
        }
        
        pstmt.executeUpdate();
        
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
        if(con != null) { try { con.close();  } catch(Exception e) {} }
        
      }
    }//end for
  }
  
  @Test
  public void loginAuth() {
    String sql = "select userid, userpw, enabled from tbl_member where userid = ?";
    
   
      Connection con = null;
      PreparedStatement pstmt = null;
      
      try {
        con = ds.getConnection();
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, "admin90");
        
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
        	System.out.println("데이터 나옴");
        	System.out.println(rs.getString("userpw"));
        	System.out.println(pwencoder.matches("pw90", rs.getString("userpw")));
        }
        
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        if(pstmt != null) { try { pstmt.close();  } catch(Exception e) {} }
        if(con != null) { try { con.close();  } catch(Exception e) {} }
        
      }

  }
}


