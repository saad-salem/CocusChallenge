package com.cocus.challenge.business.client;


import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.business.dto.clientdto.GHRepoDto;

import java.util.List;

public interface GithubApiClient {

    List<GHRepoDto> listRepos(String username);

    List<GHBranchDto> listBranches(String ownerLogin, String repoName);
}
