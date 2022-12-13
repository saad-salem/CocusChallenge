package com.cocus.challenge.business.service;

import com.cocus.challenge.business.dto.RepositoryResponse;

import java.util.List;

public interface GithubService {

    List<RepositoryResponse> listAccountRepos(String username);

}
