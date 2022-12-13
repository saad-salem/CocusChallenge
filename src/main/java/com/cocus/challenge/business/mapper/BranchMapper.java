package com.cocus.challenge.business.mapper;

import com.cocus.challenge.business.dto.BranchResponse;
import com.cocus.challenge.business.dto.clientdto.GHBranchDto;
import com.cocus.challenge.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Mapper(config = MapStructConfig.class, imports = {CollectionUtils.class})
public interface BranchMapper {

    @Mapping(source = "commit.sha", target = "commitSha")
    BranchResponse toResponse(GHBranchDto ghBranchDto);

    List<BranchResponse> toResponseList(List<GHBranchDto> ghBranchDtoList);

}
