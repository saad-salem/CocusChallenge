package com.cocus.challenge.business.dto.clientdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GHRepoDto {
    private String name;
    private GHOwnerDto owner;
    private boolean fork;
    private List<GHBranchDto> branches;
}
