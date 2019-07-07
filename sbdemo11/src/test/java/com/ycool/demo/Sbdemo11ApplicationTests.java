package com.ycool.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ycool.datamodel.User;
import com.ycool.webservice.UserController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MockServletContext.class)
@SpringBootTest(classes = {MockServletContext.class,Sbdemo11Application.class})
@WebAppConfiguration
public class Sbdemo11ApplicationTests {
    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    public void testUserController() throws Exception{

        RequestBuilder request = null;

        // 1. get user list/Get
        request = get("/users/");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        // 2. post an user with Json data/Post
        User usr = new User();
        usr.setAge(20);
        usr.setName("测试大师");
        usr.setId(1l);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String rqJson = ow.writeValueAsString(usr);

        request = post("/users/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(rqJson);
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 3. get user list again/Get
        request = get("/users/");
        mvc.perform(request)
                .andExpect(content()
                        .string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

        // 4. modify user info by id/Put
        usr.setName("测试终极大师");
        usr.setAge(30);
        rqJson = ow.writeValueAsString(usr);

        request = put("/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(rqJson);
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 5. get an user by id
        request = get("/users/1");
        mvc.perform(request)
                .andExpect(content().string("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}"));

        // 6. del删除id为1的user
        request = delete("/users/1");
        mvc.perform(request)
                .andExpect(content().string(equalTo("success")));

        // 7. get user finally
        request = get("/users/");
        mvc.perform(request)
                .andExpect(content()
                        .string(equalTo("[]")));

        // 8. verify user password
        request = post("/users/passwordcheck").param("username","yukui");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));

        request = post("/users/passwordcheck").param("password","123");
        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void contextLoads() {
    }

    @Autowired
    private DemoProperties demoProperties;

    @Test
    public void getHello(){
        Assert.assertEquals(demoProperties.getName(),"springboot测试项目");
        Assert.assertEquals(demoProperties.isFlag(),true);
    }

    public void setDemoProperties(DemoProperties demoProperties) {
        this.demoProperties = demoProperties;
    }
}
