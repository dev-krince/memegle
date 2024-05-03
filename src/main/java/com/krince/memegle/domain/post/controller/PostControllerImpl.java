package com.krince.memegle.domain.post.controller;

import com.krince.memegle.domain.post.dto.request.RequestResistPostDto;
import com.krince.memegle.domain.post.dto.response.ResponsePostListDto;
import com.krince.memegle.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostControllerImpl implements PostController{

    private final PostService postService;

    @Override
    @GetMapping()
    public ResponseEntity<List<ResponsePostListDto>> getPosts() {
        return null;
    }

    @Override
    @PostMapping()
    public ResponseEntity<Void> resistPost(RequestResistPostDto requestResistPostDto) {
        return null;
    }
}
