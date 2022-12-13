package com.cocus.challenge;

import com.cocus.challenge.business.client.GithubApiClient;
import com.cocus.challenge.business.dto.BranchResponse;
import com.cocus.challenge.business.dto.RepositoryResponse;
import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.business.dto.clientdto.GHCommitDto;
import com.cocus.challenge.business.dto.clientdto.GHOwnerDto;
import com.cocus.challenge.business.dto.clientdto.GHRepoDto;
import com.cocus.challenge.business.mapper.BranchMapper;
import com.cocus.challenge.business.service.impl.GithubServiceImpl;
import com.cocus.challenge.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class GithubServiceImplTests {

    @Mock
    GithubApiClient githubApiClient;
    @Mock
    BranchMapper branchMapper;

    @InjectMocks
    GithubServiceImpl githubService;


    @Test
    void When_GivenValidAccountOwner_Expect_ExpectedBehavior() {

        // Given
        GHOwnerDto mockedOwner = new GHOwnerDto("sa3d01");

        GHCommitDto mockedCommit = new GHCommitDto("dummy_sha");
        GHBranchDto mockedBranch = new GHBranchDto("branch-name", mockedCommit);
        List<GHBranchDto> mockedBranches = List.of(mockedBranch);

        GHRepoDto mockedRepo = new GHRepoDto("repo-name", mockedOwner, false, mockedBranches);
        List<GHRepoDto> mockedRepos = List.of(mockedRepo);
        Mockito.when(githubApiClient.listRepos("sa3d01")).thenReturn(mockedRepos);

        Mockito.when(githubApiClient.listBranches("sa3d01", "repo-name")).thenReturn(mockedBranches);

        BranchResponse mockedBranchResponse = new BranchResponse("branch-name", "dummy_sha");
        List<BranchResponse> mockedBranchResponses = List.of(mockedBranchResponse);

        Mockito.when(branchMapper.toResponseList(mockedBranches)).thenReturn(mockedBranchResponses);

        // When
        List<RepositoryResponse> response = githubService.listAccountRepos("sa3d01");

        // Then

        List<RepositoryResponse> expectedResponse = List.of(RepositoryResponse.builder()
                .repositoryName(mockedRepo.getName())
                .ownerLogin(mockedRepo.getOwner().getLogin())
                .branches(branchMapper.toResponseList(mockedBranches))
                .build()
        );

        Assertions.assertIterableEquals(response, expectedResponse);
    }

    @Test
    void When_AccountOwnerNotFound_Expect_ThrowNotFoundException() {
        // Given
        Mockito.when(githubApiClient.listRepos("wrong-user")).thenThrow(new NotFoundException());
        // When
        try {
            githubService.listAccountRepos("wrong-user");
            fail("Test fail if reach here");
        } catch (NotFoundException exception) {
            // Then
            assertEquals(exception.getMessage(), "Account Not Found");
        }
    }

}
