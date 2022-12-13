package com.cocus.challenge.integration;

import com.cocus.challenge.business.client.GithubApiClient;
import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.business.dto.clientdto.GHCommitDto;
import com.cocus.challenge.business.dto.clientdto.GHOwnerDto;
import com.cocus.challenge.business.dto.clientdto.GHRepoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public class GithubControllerTests {
    private static final String VALID_USERNAME = "sa3d01";
    private static final String INVALID_USERNAME = "wrong-user";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    GithubApiClient githubApiClient;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void GivenAccountOwnerNotFound_Expect_ThrowNotFoundException() throws Exception {
        Mockito.when(githubApiClient.listRepos(INVALID_USERNAME))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "not found", null, null, null));

        this.mockMvc.perform(get("/api/v1/github/" + INVALID_USERNAME))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void GivenXMLAcceptHeader_Expect_ThrowNotAcceptableHeader() throws Exception {
        this.mockMvc.perform(get("/api/v1/github/" + INVALID_USERNAME).accept(MediaType.APPLICATION_XML))
                .andExpect(MockMvcResultMatchers.status().is(406));
    }

    @Test
    public void GivenAccountOwnerValid_Expect_StatusOk() throws Exception {
        // Given
        GHOwnerDto mockedOwner = new GHOwnerDto("sa3d01");

        GHCommitDto mockedCommit = new GHCommitDto("dummy_sha");
        GHBranchDto mockedBranch = new GHBranchDto("branch-name", mockedCommit);
        List<GHBranchDto> mockedBranches = List.of(mockedBranch);

        GHRepoDto mockedRepo = new GHRepoDto("repo-name", mockedOwner, false, mockedBranches);
        List<GHRepoDto> mockedRepos = List.of(mockedRepo);
        Mockito.when(githubApiClient.listRepos("sa3d01")).thenReturn(mockedRepos);

        Mockito.when(githubApiClient.listBranches("sa3d01", "repo-name")).thenReturn(mockedBranches);

        this.mockMvc.perform(get("/api/v1/github/" + VALID_USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].repositoryName").value("repo-name"));
    }

}
