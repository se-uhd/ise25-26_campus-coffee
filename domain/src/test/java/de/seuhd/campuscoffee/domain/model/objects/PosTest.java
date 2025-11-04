package de.seuhd.campuscoffee.domain.model.objects;

import de.seuhd.campuscoffee.domain.exceptions.ValidationException;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PosTest {
    private static final int POSTAL_CODE_TOO_SMALL = 123;

    @Test
    void validatePostalCode_postalCodeTooSmall() {
        assertThrows(ValidationException.class, () ->
                TestFixtures.getPosFixtures().getFirst()
                        .toBuilder()
                        .postalCode(POSTAL_CODE_TOO_SMALL)
                        .build()
        );
    }
}
