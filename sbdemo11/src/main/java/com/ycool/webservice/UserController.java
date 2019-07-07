package com.ycool.webservice;

import com.ycool.datamodel.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    // 创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList(){
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id){
        return users.get(id);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String postUser(@RequestBody User user){
        users.put(user.getId(),user);
        return  "success";
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id,@RequestBody User user){
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }

    @RequestMapping(value = "/passwordcheck", method = RequestMethod.POST)
    public String verifyUserPassword(@RequestParam(value = "username") String name
            ,@RequestParam(value = "password",required = false,defaultValue = "abcd1234")String passwd){


        return "success";
    }

}
