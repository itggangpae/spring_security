package kr.co.adamsoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.co.adamsoft.dao.MemberMapper;
import kr.co.adamsoft.domain.CustomUser;
import kr.co.adamsoft.domain.MemberVO;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public UserDetails loadUserByUsername(String userName) {
		try {
			System.out.println("Load User By UserName : " + userName);
			System.out.println("Load User By UserName : " + memberMapper);
			// userName means userid
			MemberVO vo = memberMapper.read(userName);

			System.out.println("queried by member mapper: " + vo);
			return vo == null ? null : new CustomUser(vo);
		}catch(Exception e) {
			System.out.println("예외:" + e.getLocalizedMessage());
			return null;
		}
		
	} 

}
