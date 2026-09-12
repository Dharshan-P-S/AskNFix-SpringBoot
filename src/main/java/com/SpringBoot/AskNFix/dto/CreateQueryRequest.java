package com.SpringBoot.AskNFix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQueryRequest {

    private Long buildingId;
    private Long roomId;
    private String description;
    private List<QueryApplianceRequest> appliances;
}