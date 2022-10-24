package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.AWSConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Configurable
public class UserDaoFactory {

    @Bean
    public UserDao awsConnectionMaker(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsDataSource());
        return userDao;
    }

    @Bean
    public UserDao localConnectionMaker(){
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(localDataSource());
        return userDao;
    }


    @Bean
    DataSource awsDataSource(){
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl(env.get("DB_HOST"));
        dataSource.setUsername(env.get("DB_USER"));
        dataSource.setPassword(env.get("DB_PASSWORD"));
        return dataSource;

    }

    @Bean
    DataSource localDataSource(){
        //Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:3306/likelion-db");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;

    }

}
