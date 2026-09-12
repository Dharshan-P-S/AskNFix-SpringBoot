package com.SpringBoot.AskNFix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "query_appliances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueryAppliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_appliance_id")
    private Long queryApplianceId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "query_id", nullable = false)
    private Query query;

    @ManyToOne
    @JoinColumn(name = "appliance_id", nullable = false)
    private Appliance appliance;

    @Column(nullable = false)
    private Integer quantity;
}