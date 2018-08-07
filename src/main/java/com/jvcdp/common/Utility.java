package com.jvcdp.common;
import com.jvcdp.model.AppUser;
import com.jvcdp.model.UserNameNotFoundException;
import com.jvcdp.repository.ApplicationUserRepository;

import org.springframework.security.core.AuthenticationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Utility {

	public static String getRandomHash() {
		String uuid = UUID.randomUUID().toString();
        return  uuid.replaceAll("-", "");
	}
	
    //Takes a string, and converts it to md5 hashed string.
    public static String md5Hash(String message, String salt) {
        String md5 = "";
        if(null == message) 
            return null;
         
        message = message+salt;//adding a salt to the string before it gets hashed.
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");//Create MessageDigest object for MD5
            digest.update(message.getBytes(), 0, message.length());//Update input string in message digest
            md5 = new BigInteger(1, digest.digest()).toString(16);//Converts message digest value in base 16 (hex)
  
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static boolean authenticate(ApplicationUserRepository applicationUserRepository, String userName, String password) throws AuthenticationException {
        Iterator<AppUser> it =applicationUserRepository.findAll().iterator();

        while(it.hasNext())
        {
            AppUser itm = it.next();

            if(itm.getUserName().equals( userName) ){
                String salt = itm.getSalt();
                String hash = Utility.md5Hash(password, salt);
                if (itm.getPasswordHash().equalsIgnoreCase(hash)) {
                    return true;
                }
            }else{
                throw new UserNameNotFoundException("Username does not exist in our database!");
            }
        }

        return false;
    }
}
