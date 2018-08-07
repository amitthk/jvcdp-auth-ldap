package com.jvcdp.controller;

import java.util.Iterator;
import java.util.List;

import com.jvcdp.model.AppUser;
import com.jvcdp.model.EmailExistsException;
import com.jvcdp.model.UserNameAlreadyAllotted;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvcdp.repository.ApplicationUserRepository;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@GetMapping(value = "")
	public Iterable<AppUser> list() {
		return applicationUserRepository.findAll();
	}

	@GetMapping(value = "/username/{userName}")
	public AppUser ApplicationUserByUserName(@PathVariable String userName) {
		Iterator<AppUser> it =applicationUserRepository.findAll().iterator();

		while(it.hasNext())
		{
			AppUser itm = it.next();
			if(itm.getUserName().equals(userName )){
				return itm;
			}
		}
		return null;
	}

	@PostMapping(value = "")
	public AppUser create(@RequestBody AppUser AppUser) {
		return applicationUserRepository.save(AppUser);
	}

	@GetMapping(value = "/{id}")
	public AppUser get(@PathVariable Long id) {
		return applicationUserRepository.findOne(id);
	}

	@PutMapping(value = "/{id}")
	public AppUser update(@PathVariable Long id, @RequestBody AppUser AppUser) {
		AppUser existingApplicationUser = applicationUserRepository.findOne(id);
		BeanUtils.copyProperties(AppUser, existingApplicationUser);
		return applicationUserRepository.save(existingApplicationUser);
	}

	@DeleteMapping(value = "/{id}")
	public AppUser delete(@PathVariable Long id) {
		AppUser existingApplicationUser = applicationUserRepository.findOne(id);
		applicationUserRepository.delete(existingApplicationUser);
		return existingApplicationUser;
	}
	
}
