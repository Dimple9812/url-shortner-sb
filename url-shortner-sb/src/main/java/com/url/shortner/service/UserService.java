package com.url.shortner.service;
import com.url.shortner.dtos.LoginRequest;
import com.url.shortner.models.User;
import com.url.shortner.repository.UserRepository;
import com.url.shortner.security.jwt.JwtAuthenticationResponse;
import com.url.shortner.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;


    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest){
        //authenticationManager processes auth req comes from SS, it has authenticate method
        Authentication authentication = authenticationManager.authenticate(
                //1. Creating an instance of "UsernamePasswordAuthenticationToken" class given by SS,
                //and then instance of this is passed to authenticate method in first line above
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())); //
        //if username and pd are valid, then a fully populated authentication obj is returned
        //How SS knows username , pd are valid? ans - websecurityconfig class,here we configured daoauthprovider
        //which tells SS that u have to make use of userDetailsService impl and it is overriding the loadUserByUsername
        //It tells SS this is how users nd pds are suppose to be found.
        //After this we set securitycontext, means the ss construct will hold the auth data for current req or session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //Generate token, we need instance of userDetails, using Getprincipal method of authentication
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails); //token returned as a string
        return new JwtAuthenticationResponse(jwt); //crafting a obj of authresponse
    }

    //retreive user info based on username
    public User findByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + name)
        );

    }
}
