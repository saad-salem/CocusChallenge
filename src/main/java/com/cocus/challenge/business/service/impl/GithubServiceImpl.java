package com.cocus.challenge.business.service.impl;

import com.cocus.challenge.business.client.GithubApiClient;
import com.cocus.challenge.business.dto.RepositoryResponse;
import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.business.dto.clientdto.GHRepoDto;
import com.cocus.challenge.business.mapper.BranchMapper;
import com.cocus.challenge.business.service.GithubService;
import com.cocus.challenge.exception.NotFoundException;
import com.cocus.challenge.exception.RateLimitExceedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubServiceImpl implements GithubService {

    @Autowired
    GithubApiClient githubApiClient;
    @Autowired
    BranchMapper branchMapper;

    @Override
    public List<RepositoryResponse> listAccountRepos(String username) {
        try {
            List<GHRepoDto> repos = githubApiClient.listRepos(username);
            List<RepositoryResponse> repositoryResponseList = new ArrayList<>();
            for (GHRepoDto repo : repos) {
                if (!repo.isFork()) {
                    List<GHBranchDto> branches = githubApiClient.listBranches(repo.getOwner().getLogin(), repo.getName());

                    RepositoryResponse repositoryResponse = RepositoryResponse.builder()
                            .repositoryName(repo.getName())
                            .ownerLogin(repo.getOwner().getLogin())
                            .branches(branchMapper.toResponseList(branches))
                            .build();

                    repositoryResponseList.add(repositoryResponse);
                }
            }
            return repositoryResponseList;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException();
        } catch (HttpClientErrorException.Forbidden e) {
            throw new RateLimitExceedException();
        }
    }
}
