package com.SpringBoot.AskNFix.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_number", nullable = false, length = 20)
    private String roomNumber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(length = 50)
    private String floor;

    @JsonIgnore
    @OneToMany(
            mappedBy = "room",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<RoomAppliance> roomAppliances = new ArrayList<>();
}