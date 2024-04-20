package co.istad.springsecuritybasic.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminRestController {
    @GetMapping
    public String getAllUser(){
        return "All Users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){
        return "User"+id+"has been Deleted ";
    }
}
