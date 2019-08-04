package com.alag.agmall.business.module.user.api;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.user.api.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public interface UserController {

    @GetMapping(value = "login")
    ServerResponse login(
            @RequestParam(required = true, value = "username") String username,
            @RequestParam(required = true, value = "password") String password);



    @GetMapping("logout")
    ServerResponse logout();

    @PostMapping("register")
    ServerResponse register(@RequestBody User user);

    @PostMapping("check_valid")
    ServerResponse checkValid(@RequestParam(required = true,value = "str") String str,
                              @RequestParam(required = true,value = "type") String type);

    @GetMapping("get_user_info")
    ServerResponse<User> getUserInfo();

    @GetMapping("get_question")
    ServerResponse<String> getQuestion(@RequestParam("username") String username);

    @PostMapping("check_answer")
    ServerResponse checkAnswer(@RequestParam(required = true,value = "username") String username,
                               @RequestParam(required = true,value = "question") String question,
                               @RequestParam(required = true,value = "answer") String answer);

    @PostMapping("reset_password")
    ServerResponse resetPassword(@RequestParam(required = true,value = "username") String username,
                                 @RequestParam(required = true,value = "newPasswd") String newPasswd,
                                 @RequestParam(required = true,value = "token") String token);

    @PostMapping("online_reset_password")
    ServerResponse resetPasswordBySession(@RequestParam(required = true,value = "oldPasswd") String oldPasswd,
                                          @RequestParam(required = true,value = "newPasswd") String newPasswd);

    @PostMapping("update_user_info")
    ServerResponse updateUserInfo(@RequestBody User user);

    @GetMapping("get_information")
    ServerResponse<User> getInfomation();
}
