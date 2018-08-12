package com.jvcdp.repository;

import com.jvcdp.model.AppLdapUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class LdapAccessRepository {

    private static final String DOMAIN_NAME = "Dom215-01";
    private static final String DOMAIN_ROOT = "DC=Dom215-01,DC=local";
    private static final String DOMAIN_URL = "ldap://10.18.215.112:389";
    private static final String ADMIN_NAME = "CN=Administrator,CN=Users,DC=Dom215-01,DC=local";
    private static final String ADMIN_PASS = "g18";


    private LdapContext context;

    @Autowired
    private LdapTemplate ldapTemplate;


    public LdapAccessRepository() {

        Hashtable<String, String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        // set security credentials, note using simple cleartext authentication
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ADMIN_NAME);
        env.put(Context.SECURITY_CREDENTIALS, ADMIN_PASS);

        // connect to my domain controller
        env.put(Context.PROVIDER_URL, DOMAIN_URL);

        try {
            this.context = new InitialLdapContext(env, null);
        } catch (NamingException e) {
            System.err.println("Problem creating object: ");
            e.printStackTrace();
        }

    }

    public boolean addUser(AppLdapUser appLdapUser) throws NamingException {

        // Create a container set of attributes
        Attributes container = new BasicAttributes();

        // Create the objectclass to add
        Attribute objClasses = new BasicAttribute("objectClass");
        objClasses.add("top");
        objClasses.add("person");
        objClasses.add("organizationalPerson");
        objClasses.add("user");

        // Assign the username, first name, and last name
        String cnValue = new StringBuffer(appLdapUser.getFirstName()).append(" ").append(appLdapUser.getLastName()).toString();
        Attribute cn = new BasicAttribute("cn", cnValue);
        Attribute sAMAccountName = new BasicAttribute("sAMAccountName", appLdapUser.getUserName());
        Attribute principalName = new BasicAttribute("userPrincipalName", appLdapUser.getUserName()
                + "@" + DOMAIN_NAME);
        Attribute givenName = new BasicAttribute("givenName", appLdapUser.getFirstName());
        Attribute sn = new BasicAttribute("sn", appLdapUser.getLastName());
        Attribute uid = new BasicAttribute("uid", appLdapUser.getUserName());

        // Add password
        Attribute userPassword = new BasicAttribute("userpassword", appLdapUser.getPassword());

        // Add these to the container
        container.put(objClasses);
        container.put(sAMAccountName);
        container.put(principalName);
        container.put(cn);
        container.put(sn);
        container.put(givenName);
        container.put(uid);
        container.put(userPassword);

        // Create the entry
        try {
            context.createSubcontext(getUserDN(cnValue, appLdapUser.getOrganisationUnit()), container);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getUserDN(String aUsername, String aOU) {
        return "cn=" + aUsername + ",ou=" + aOU + "," + DOMAIN_ROOT;
    }

    public boolean findUser(String userName, String password) {
        return(getAllPersonNames().contains(userName));
    }

    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }
}
