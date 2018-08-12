package com.jvcdp.controller;


import com.jvcdp.model.*;
import com.jvcdp.repository.LdapAccessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequestMapping("auth")
public class AuthController {

	Logger logger = LoggerFactory.getLogger(AuthController.class);


	@Autowired
	LdapAccessRepository ldapAccessRepository;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(){
	    return ("Welcome to jvcdp ldapauth api!");
    }

	@RequestMapping(value="/login", method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String login(@RequestBody AccountCredentials appUser) throws AuthenticationException{

		if(ldapAccessRepository.findUser(appUser.getUserName(), appUser.getPassword())){
		    //Update the login timestamp here
			return ("Login Successfull!");
		}else{
			throw new InvalidCredentialsException("Unable to login!");
		}
	}

	@RequestMapping(value="/register", method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public AppLdapUser register(@Valid @RequestBody AccountCredentials appUser) throws AuthenticationException{

//		if(ldapAccessRepository.findUser(appUser.getUserName(),appUser.getPassword()))
//		{
//			if(itm.getEmail().equals(appUser.getEmailAddress())){
//			throw new EmailExistsException("There is an account with that email address");
//			}
//			if(itm.getUserName().equals( appUser.getUserName()) ){
//				throw new UserNameAlreadyAllotted("This UserName is already taken!");
//		}
//        }

		try{
			AppLdapUser newUser= new AppLdapUser();
			newUser.setUserName(appUser.getUserName());
			newUser.setFirstName(appUser.getEmailAddress());
			newUser.setOrganisationUnit(appUser.getApiId());
			newUser.setPassword(appUser.getPassword());

			ldapAccessRepository.addUser(newUser);
			return(newUser);
		}catch(Exception exc){
			logger.error(exc.getStackTrace().toString());
		}
		return null;
	}

}
