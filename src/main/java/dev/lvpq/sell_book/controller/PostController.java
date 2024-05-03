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

    @GetMapping
    String getPost(Model model) {
        var posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/postDetail")
    String getPostDetail(Model model, @RequestParam("id") String postId) {
        PostDetailResponse postDetailResponse = postService.getById(postId);
        model.addAttribute("postDetailRep", postDetailResponse);
        return "/layout/postDetail";
    }

    @GetMapping("/addPost")
    String getAddPost(Model model) {
        AddPostRequest user = new AddPostRequest();
        model.addAttribute("user", user);
        return "add/addPost";
    }

//    Fix again here
    @PostMapping("/addPost")
    String postAddPost(@Valid @ModelAttribute("user") AddPostRequest user) {
        var request = postMapper.toAddPostRequest(user);
        postService.create(request);
        return "redirect:/post";
    }

    @GetMapping("/delete/{pageNumber}")
    String deletePost(@PathVariable String pageNumber) {
        postService.delete(pageNumber);
        return "redirect:/post";
    }
}
