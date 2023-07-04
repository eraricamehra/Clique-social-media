package com.neu.esd.clique.service;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.neu.esd.clique.model.User;


public class UserValidator implements Validator {
	
	@Override
	public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

	@Override
	public void validate(Object target, Errors errors) {
//		 ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
	        User user = (User) target;
	        if (user.getEmail() == null || user.getEmail().isBlank()) {
	        	errors.rejectValue("email", "cannotbeblack", "Email cannot be blank");
	        } else if (user.getPassword() == null || user.getPassword().isBlank()) {
	        	errors.rejectValue("password", "cannotbeblack", "Password cannot be blank");
	        }
		
	}

}
