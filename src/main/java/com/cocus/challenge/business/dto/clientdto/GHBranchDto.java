package com.cocus.challenge.business.dto.clientdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GHBranchDto {
    private String name;
    private GHCommitDto commit;
}
