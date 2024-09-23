package com.react.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Config.TokenProvider;
import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Repository.UserRepository;
import com.react.Response.AuthResponse;

@RestController
public class HomeController {
	

}
