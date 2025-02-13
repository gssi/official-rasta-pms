package it.gssi.cs.rastapms.presentation.backoffice;

import io.swagger.v3.oas.annotations.Hidden;
import it.gssi.cs.rastapms.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.UserService;

@Hidden
@Controller
@RequestMapping("backoffice/common/profile")
public class ProfileController {

	private UserService userService;

	public ProfileController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String updateProfileStart(Model model) throws BusinessException {
		User user = Utility.getUser();
		User newUser = userService.findUserByUsername(user.getUsername());
		model.addAttribute("profile", newUser);

		return "backoffice/common/profile";

	}

	@PostMapping
	public String modificaProfile(@ModelAttribute User newProfile) throws BusinessException {
		User user = Utility.getUser();
		newProfile.setId(user.getId());
		userService.updateProfile(newProfile);
		return "redirect:/backoffice/common/operazioneok";

	}

}
