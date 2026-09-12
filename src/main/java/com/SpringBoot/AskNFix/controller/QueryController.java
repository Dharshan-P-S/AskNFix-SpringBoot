package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.dto.CreateQueryRequest;
import com.SpringBoot.AskNFix.entity.Query;
import com.SpringBoot.AskNFix.entity.QueryAppliance;
import com.SpringBoot.AskNFix.repository.QueryApplianceRepository;
import com.SpringBoot.AskNFix.service.QueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

    private final QueryService queryService;
    private final QueryApplianceRepository queryApplianceRepository;

    public QueryController(QueryService queryService, QueryApplianceRepository queryApplianceRepository) {
        this.queryService = queryService;
        this.queryApplianceRepository = queryApplianceRepository;
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public Query createQuery(
            Authentication authentication,
            @RequestBody CreateQueryRequest request) {

        return queryService.createQuery(
                authentication.getName(),
                request
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Query> getAllQueries() {
        return queryService.getAllQueries();
    }

    @GetMapping("/{queryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public Query getQueryById(
            @PathVariable Long queryId,
            Authentication authentication) {

        if (authentication.getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"))) {

            return queryService.getQueryById(queryId);
        }

        return queryService.getQueryByIdForStudent(
                queryId,
                authentication.getName()
        );
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public List<Query> getStudentQueries(
            Authentication authentication) {

        return queryService.getStudentQueries(
                authentication.getName()
        );
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Query> getPendingQueries() {
        return queryService.getPendingQueries();
    }

    @GetMapping("/completed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Query> getCompletedQueries() {
        return queryService.getCompletedQueries();
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('WORKER')")
    public List<Query> getStaffQueries(
            Authentication authentication) {

        return queryService.getStaffQueries(
                authentication.getName()
        );
    }

    @PutMapping("/{queryId}/assign/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Query assignQuery(
            @PathVariable Long queryId,
            @PathVariable Long staffId) {

        return queryService.assignQuery(queryId, staffId);
    }

    @PutMapping("/{queryId}/start")
    @PreAuthorize("hasRole('WORKER')")
    public Query startQuery(
            @PathVariable Long queryId,
            Authentication authentication) {

        return queryService.startQuery(
                queryId,
                authentication.getName()
        );
    }

    @PutMapping("/{queryId}/complete")
    @PreAuthorize("hasRole('WORKER')")
    public Query completeQuery(
            @PathVariable Long queryId,
            @RequestParam String completionDescription,
            Authentication authentication) {

        return queryService.completeQuery(
                queryId,
                completionDescription,
                authentication.getName()
        );
    }

    @GetMapping("/room/{roomId}/appliances")
    @PreAuthorize("hasRole('STUDENT')")
    public List<QueryAppliance> getRoomQueryAppliances(
            @PathVariable Long roomId) {

        return queryApplianceRepository.findByQueryRoomRoomId(roomId);
    }
}