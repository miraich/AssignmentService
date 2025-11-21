package com.andrey.assignmentservice.model;

import com.andrey.assignmentservice.enums.PullRequestStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PullRequest {
    @Id
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private PullRequestStatus status;

    @ManyToMany
    @JoinTable(
            name = "pr_reviewers",
            joinColumns = @JoinColumn(name = "pull_request_id"),
            inverseJoinColumns = @JoinColumn(name = "reviewer_id")
    )
    private Set<User> assignedReviewers;

    private Instant mergedAt;
}
