package dev.lvpq.sell_book.controller;

import dev.lvpq.sell_book.dto.request.AddPostRequest;
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
    String getPost(Model model, @RequestParam(name = "searchKey", required = false) String searchKey) {
        var posts = postService.getAllWithKey(searchKey);
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/postDetail/{id}")
    String getPostDetail(Model model, @PathVariable String id) {
        var postResponse = postService.getById(id);
        model.addAttribute("postDetailRep", postResponse);
        return "/layout/postDetail";
    }

    @GetMapping("/addPost")
    String getAddPost(Model model) {
        AddPostRequest post = new AddPostRequest();
        model.addAttribute("post", post);
        return "add/addPost";
    }
    @PostMapping("/addPost")
    String postAddPost(@Valid @ModelAttribute("post") AddPostRequest post) {
        var request = postMapper.toAddPostRequest(post);
        postService.create(request);
        return "redirect:/post";
    }

    @GetMapping("/delete/{id}")
    String deletePost(@PathVariable String id) {
        postService.delete(id);
        return "redirect:/post";
    }

    @GetMapping("/update/{id}")
    String getUpdatePost(@PathVariable String id, Model model) {
        AddPostRequest post = new AddPostRequest();
        var prePost = postService.getById(id);
        model.addAttribute("prePost", prePost);
        model.addAttribute("post", post);
        model.addAttribute("id", id);
        return "add/updatePost";
    }

    @PostMapping("/update/{id}")
    String postUpdatePost(@Valid @ModelAttribute("post") AddPostRequest post, @PathVariable String id) {
        log.info(id);
        var request = postMapper.toAddPostRequest(post);
        postService.update(request, id);
        return "redirect:/post";
    }
}
