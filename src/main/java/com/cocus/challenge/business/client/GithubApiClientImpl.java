package com.cocus.challenge.business.client;

import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.business.dto.clientdto.GHRepoDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GithubApiClientImpl implements GithubApiClient {

    @Value("${github.api.url}")
    private String base_url;
    @Value("${github.token}")
    private String token;

    @Autowired
    RestTemplate restTemplate;

    public HttpEntity<String> getEntity() {
        if (token.isBlank()) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(headers);
    }
    @Override
    public List<GHRepoDto> listRepos(String username) {
        String uri = base_url + "users/" + username + "/repos";
        ResponseEntity<List<GHRepoDto>> result = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getEntity(),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getStatusCode().value() == 200 ? result.getBody() : null;
    }

    @Override
    public List<GHBranchDto> listBranches(String ownerLogin, String repoName) {
        String uri = base_url + "/repos/" + ownerLogin + "/" + repoName + "/branches";
        ResponseEntity<List<GHBranchDto>> result = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                getEntity(),
                new ParameterizedTypeReference<>() {
                }
        );
        return result.getStatusCode().value() == 200 ? result.getBody() : null;
    }
}
