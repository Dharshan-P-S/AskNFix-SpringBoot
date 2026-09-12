package com.SpringBoot.AskNFix.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appliances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appliance_id")
    private Long applianceId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;
}