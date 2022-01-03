package kr.co.adamsoft;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommonController {

	@GetMapping("/accesserror")
	public String accessDenied(Authentication auth, Model model) {
		System.out.println("access Denied : " + auth);
		model.addAttribute("msg", "Access Denied");
		return "accesserror";
	}
	
	@GetMapping("/customlogin")
	public void loginInput(String error, String logout, Model model) {
		System.out.println("error: " + error);
		System.out.println("logout: " + logout);

		if (error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}

		if (logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
	
	@GetMapping("/customlogout")
	public void logoutGET() {
		System.out.println("custom logout");
	}

	@PostMapping("/customlogout")
	public void logoutPost() {
		System.out.println("post custom logout");
	}

}