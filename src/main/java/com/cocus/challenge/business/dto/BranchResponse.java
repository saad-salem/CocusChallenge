package com.cocus.challenge.business.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private String name;
    private String commitSha;
}
