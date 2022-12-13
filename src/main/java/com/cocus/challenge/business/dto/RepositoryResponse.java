package com.cocus.challenge.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryResponse {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchResponse> branches;
}
