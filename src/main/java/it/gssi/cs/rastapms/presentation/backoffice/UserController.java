package it.gssi.cs.rastapms.presentation.backoffice;

import io.swagger.v3.oas.annotations.Hidden;
import it.gssi.cs.rastapms.business.BusinessException;
import it.gssi.cs.rastapms.business.RequestGrid;
import it.gssi.cs.rastapms.business.ResponseGrid;
import it.gssi.cs.rastapms.business.UserService;
import it.gssi.cs.rastapms.domain.Role;
import it.gssi.cs.rastapms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Hidden
@Controller
@RequestMapping("backoffice/user")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    public String list() {
        return "backoffice/user/list";
    }

    @PostMapping("/findallpaginated")
    public @ResponseBody ResponseGrid<User> findAllPaginated(@RequestBody RequestGrid requestGrid) throws BusinessException {
        return userService.findAllUsersPaginated(requestGrid);
    }

    @GetMapping("/create")
    public String createStart(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "backoffice/user/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") User user) throws BusinessException, IOException {
        Role role = userService.findRoleByName(Role.ADMIN_NAME);
        String encodedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        user.addRole(role);
        userService.createUser(user);
        return "redirect:/backoffice/user/list";
    }

    @GetMapping("/update")
    public String updateStart(Model model, @RequestParam("id") Long id) throws BusinessException {
        User user = userService.findUserByID(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "backoffice/user/form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user) throws BusinessException, IOException {
        Role role = userService.findRoleByName(Role.ADMIN_NAME);
        String encodedPwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        user.addRole(role);
        userService.createUser(user);
        return "redirect:/backoffice/user/list";
    }

    @GetMapping("/delete")
    public String deleteStart(Model model, @RequestParam("id") Long id) throws BusinessException {
        User user = userService.findUserByID(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "backoffice/user/form";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("user") User user) throws BusinessException, IOException {
        userService.deleteUser(user);
        return "redirect:/backoffice/user/list";
    }
}
