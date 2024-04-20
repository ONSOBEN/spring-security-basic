package co.istad.springsecuritybasic.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article")
public class ArticleRestController {
    @GetMapping
    public String getAllArticles(){
        return "All Articles";
    }
    @GetMapping("/read/{id}")
    public String readingSingleArticle(@PathVariable("id") int id){
        return "Reading Single Article";
    }
    @PostMapping("/write")
    public String createArticle(){
        return "Created  Article";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") int id){
        return "Deleted Article";
    }

}
