package com.SpringBoot.AskNFix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryApplianceRequest {

    private Long applianceId;
    private Integer quantity;
}