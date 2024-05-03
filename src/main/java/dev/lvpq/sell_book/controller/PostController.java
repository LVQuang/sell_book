package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.dto.request.AddPostRequest;
import dev.lvpq.sell_book.dto.response.PostDetailResponse;
import dev.lvpq.sell_book.mapper.PostMapper;
import dev.lvpq.sell_book.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {
    PostService postService;
    PostMapper postMapper;

    @GetMapping("/{pageNumber}")
    String getPost(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {
        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if(postService.getAll().isEmpty())
            return "index";

        var result = postService.getAllPostsPage(pageNumber);
        var posts = result.getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages",
                result.getTotalPages());
        model.addAttribute("currentPage", pageNumber + 1);
        if(result.getTotalPages() == 0) {
            return "index";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/post/null?page=0&outPage=true";
        return "index";
    }

    @GetMapping("/myPost/{pageNumber}")
    String getMyPost(Model model,
                   @RequestParam(name = "page",
                           required = false, defaultValue = "0") Integer pageNumber
    )
    {

        if (pageNumber != null && pageNumber > 0)
            pageNumber-=1;
        if(pageNumber == null)
            pageNumber =0;

        if(postService.getMyPosts().isEmpty()) {
            log.info("Check my posts");
            return "layout/myPosts";
        }

        var result = postService.getAllMyPostPage(pageNumber);
        var posts = result.getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages",
                result.getTotalPages());
        if(result.getTotalPages() == 0) {
            return "layout/myPosts";
        }
        if(result.getTotalPages() <= pageNumber)
            return "redirect:/post/myPost/null?page=0&outPage=true";
        return "layout/myPosts";
    }

    @GetMapping("/postDetail")
    String getPostDetail(Model model, @RequestParam("id") String postId)
    {
        PostDetailResponse postDetailResponse = postService.getById(postId);
        model.addAttribute("postDetailRep", postDetailResponse);
        return "/layout/postDetail";
    }

    @GetMapping("/addPost")
    String getAddPost(Model model)
    {
        AddPostRequest user = new AddPostRequest();
        model.addAttribute("user", user);
        return "add/addPost";
    }

    @PostMapping("/addPost")
    String postAddPost(@Valid @ModelAttribute("user") AddPostRequest user){
        var request = postMapper.toAddPostRequest(user);
        postService.create(request);
        return "redirect:/post/myPost/null?page=0";
    }

    @GetMapping("/delete/{pageNumber}")
    String deletePost(@PathVariable String pageNumber) {
        postService.delete(pageNumber);
        return "redirect:/post/myPost/null?page=0";
    }
}
