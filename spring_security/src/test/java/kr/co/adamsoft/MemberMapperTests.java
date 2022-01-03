package kr.co.adamsoft;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.adamsoft.dao.MemberMapper;
import kr.co.adamsoft.domain.MemberVO;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class MemberMapperTests {

  @Autowired
  private MemberMapper mapper;
  
  
  @Test
  public void testRead() {
    MemberVO vo = mapper.read("admin90");
    System.out.println(vo);
    vo.getAuthList().forEach(authVO -> System.out.println(authVO));
  }
  
}


