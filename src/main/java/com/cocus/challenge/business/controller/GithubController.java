package com.cocus.challenge.business.controller;

import com.cocus.challenge.business.dto.RepositoryResponse;
import com.cocus.challenge.business.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/github")
@Validated
public class GithubController {

    @Autowired
    GithubService githubService;

    @GetMapping(value = "/{username}")
    public List<RepositoryResponse> listAccountRepos(@PathVariable String username) {
        return githubService.listAccountRepos(username);
    }

}
