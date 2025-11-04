package de.seuhd.campuscoffee.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * DTO record for POS metadata.
 */
@Builder(toBuilder = true)
public record ReviewDto (
    @Nullable Long id,
    @Nullable LocalDateTime createdAt,
    @Nullable LocalDateTime updatedAt,

    @NotNull(message = "POS ID cannot be null.")
    @NonNull Long posId,

    @NotNull(message = "Author ID cannot be null.")
    @NonNull Long authorId,

    @NotBlank(message = "Review text cannot be empty.")
    @Size(min = 10, max = 5000, message = "Review must be between 10 and 5000 characters long.")

    @NonNull String review,

    @Nullable Boolean approved // missing when creating a new review
) implements Dto<Long> {
    @Override
    public @Nullable Long getId() {
        return id;
    }
}
