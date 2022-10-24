package com.likelion.dao_project.dao;

import com.likelion.dao_project.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;



    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("localConnectionMaker", UserDao.class);

        // 픽스처: 여러 테스트에서 반복적으로 사용되는 부분 -> BeforeEach 를 이용해서 생성해두면 편리함
        this.user1 = new User("121", "박성철", "61321");
        this.user2 = new User("122", "이길원", "82465");
        this.user3 = new User("123", "박범진", "55064");

    }


    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
        User user = new User("9", "EternityHwan", "11234");
        userDao.add(user);

        User selectedUser = userDao.findById("9");
        Assertions.assertEquals(user.getName(), selectedUser.getName());
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {

        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        User user = new User("101", "박성철", "1122345");

        userDao.add(user);
        assertEquals(userDao.getCount(), 1);

        User user2 = userDao.findById("101");

        assertEquals(user2.getName(), "박성철");

    }

    @Test
    void count() throws SQLException, ClassNotFoundException {

        userDao.deleteAll();
        assertEquals(userDao.getCount(), 0);

        userDao.add(user1);
        assertEquals(userDao.getCount(), 1);

        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
    }





}