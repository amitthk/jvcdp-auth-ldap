package com.jvcdp.repository;

import com.jvcdp.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ApplicationUserRepository extends CrudRepository<AppUser, Long> {

//    @Query("select count(e) from AppUser e")
//    int countApplicationUsers();
//
//    @Query("select count(e) FROM AppUser e where e.userName=:userName")
//    int countApplicationUsersByUserName(@Param("userName")String userName);
//
//
//    @Query("select e FROM AppUser e where e.userName=:userName")
//    List<AppUser> getApplicationUsersByUserName(@Param("userName")String userName);
//
//
//    @Query("select count(e) FROM AppUser e where e.email=:email")
//    int countApplicationUsersByEmail(@Param("email")String email);
//
//
//    @Query("select e FROM AppUser e where e.email=:email")
//    List<AppUser> getApplicationUsersByEmail(@Param("email")String email);
}
