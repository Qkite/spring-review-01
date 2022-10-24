package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.AWSConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class UserDaoFactory {

    @Bean
    public UserDao awsConnectionMaker(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao();
        return userDao;
    }


    @Bean
    public UserDao localConnectionMaker(){
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao();
        return userDao;
    }


}
