package com.praveen.myWebApp.rest;

import com.praveen.myWebApp.Email.EmailSender;
import com.praveen.myWebApp.entity.Content;
import com.praveen.myWebApp.entity.User;
import com.praveen.myWebApp.service.ContentService;
import com.praveen.myWebApp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.praveen.myWebApp.config.UserInfo;


import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class FrontController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public String findAllContent(Model model){

        UserInfo user= (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User currUser=user.getUser();

        if(currUser.getNewUser()){
            currUser.setNewUser(false);
            userService.save(currUser);
            return "welcomePage";
        }
        model.addAttribute("List",contentService.findAll());

        return "home";

    }
    @RequestMapping("/content/{id}")
    public String showContent(@PathVariable("id") String id,Model model){
        Optional<Content> content=contentService.findById(Integer.parseInt(id));
        String code=content.get().getCode();
        model.addAttribute("code",code);
        model.addAttribute("id",id);
        return "displaycode";
    }

    @RequestMapping("/newContent")
    public String findAllThe(@ModelAttribute Content content){
        return "newContent";
    }
    @PostMapping(value = "/saveContent")
    public String saveContent(@ModelAttribute Content content){
        contentService.save(content);
        return "redirect:/home";
    }
    @RequestMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public String hello(){
        return "hello";
    }

   @RequestMapping("/edit/{id}")
    public String editContent(@PathVariable("id") String id,Model model){
        Optional<Content> content=contentService.findById(Integer.parseInt(id));
        if(content.isEmpty()){
            throw new NoSuchElementException("no such field in the database");
        }
        model.addAttribute("content",content.get());
        model.addAttribute("id",id);
        return "newContent";
   }

   @RequestMapping("/delete/{id}")
    public String deleteContent(@PathVariable("id") String id){

        Optional<Content> content=contentService.findById(Integer.parseInt(id));

        if(content.isPresent()){

            contentService.delete(content.get());

        }

        return "redirect:/home";

   }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {

        Optional<Content> code = contentService.findById(id);


        Resource resource = new ByteArrayResource(code.get().getCode().getBytes());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + code.get().getName() + "\"")
                .body(resource);
    }

    @RequestMapping("/signUp")
    public String signUp(@ModelAttribute User user){
        return "signUp";
    }
    @PostMapping("/newUser")
    public String addNewUser(@ModelAttribute User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(user.getUserName());

        userService.save(user);

        return "/login";

    }
    @RequestMapping("/loginPage")
    public String login(){

        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/loginPage";
    }

    @RequestMapping("/share/{id}")
    public String share(@PathVariable("id") String id,Model model){
        model.addAttribute("id",id);
        return "share";
    }

    @PostMapping("/send")
    public String sendViaEmail(@RequestParam("email") String email, @RequestParam("id") String id, Authentication authentication){

        UserInfo user= (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        String userMail=user.getUser().getEmail();

        Optional<Content> content=contentService.findById(Integer.parseInt(id));

        emailSender.sendMail(email,"Code",content.get().getCode(),userMail);

        return "redirect:/home";

    }

}
