package de.seuhd.campuscoffee.domain.model.objects;

import lombok.Builder;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * Domain record that stores a review for a point of sale.
 * Reviews are approved once they received a configurable number of approvals.
 *
 * @param id            the unique identifier of the review, null when not yet persisted
 * @param createdAt     the timestamp when the review was created, set on creation
 * @param updatedAt     the timestamp when the review was last updated, set on creation and update
 * @param pos           the point of sale being reviewed
 * @param author        the user who authored the review
 * @param review        the text content of the review
 * @param approvalCount the number of approvals this review has received, updated by the domain module
 * @param approved      whether the review is approved, determined by the domain module
 */
@Builder(toBuilder = true)
public record Review(
        @Nullable Long id, // null when the review has not been created yet
        @Nullable LocalDateTime createdAt, // set on review creation
        @Nullable LocalDateTime updatedAt, // set on review creation and update
        @NonNull Pos pos,
        @NonNull User author,
        @NonNull String review,
        @NonNull Integer approvalCount, // is updated by the domain module
        @NonNull Boolean approved // is determined by the domain module
) implements DomainModel<Long> {
    @Override
    public Long getId() {
        return id;
    }
}
