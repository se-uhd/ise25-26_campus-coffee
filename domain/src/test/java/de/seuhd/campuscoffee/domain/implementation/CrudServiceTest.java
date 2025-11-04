package de.seuhd.campuscoffee.domain.implementation;

import de.seuhd.campuscoffee.domain.exceptions.DuplicationException;
import de.seuhd.campuscoffee.domain.exceptions.NotFoundException;
import de.seuhd.campuscoffee.domain.model.objects.DomainModel;
import de.seuhd.campuscoffee.domain.ports.data.CrudDataService;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the abstract CrudServiceImpl base class.
 * Uses test-only implementations to test the abstract class in isolation.
 */
@ExtendWith(MockitoExtension.class)
public class CrudServiceTest {

    /**
     * Test-only domain model for testing CrudServiceImpl.
     */
    private record TestDomain(@Nullable Long id, String name) implements DomainModel<Long> {
        @Override
        public @Nullable Long getId() {
            return id;
        }
    }

    /**
     * Test-only concrete implementation of CrudServiceImpl for testing.
     */
    private static class TestCrudServiceImpl extends CrudServiceImpl<TestDomain, Long> {
        private final CrudDataService<TestDomain, Long> dataService;

        TestCrudServiceImpl(CrudDataService<TestDomain, Long> dataService) {
            super(TestDomain.class);
            this.dataService = dataService;
        }

        @Override
        protected CrudDataService<TestDomain, Long> dataService() {
            return dataService;
        }
    }

    @Mock
    private CrudDataService<TestDomain, Long> dataService;

    @InjectMocks
    private TestCrudServiceImpl crudService;

    /**
     * Tests that upsert() rethrows DuplicationException from the data service.
     */
    @Test
    void upsertRethrowsDuplicationException() {
        // given
        TestDomain object = new TestDomain(null, "duplicate");
        when(dataService.upsert(object)).thenThrow(
                new DuplicationException(TestDomain.class, "name", "duplicate")
        );

        // when, then
        assertThrows(DuplicationException.class, () -> crudService.upsert(object));
        verify(dataService).upsert(object);
    }

    /**
     * Tests that delete() delegates to the data service.
     */
    @Test
    void deleteDelegatesToDataService() {
        // given
        doNothing().when(dataService).delete(1L); // delete is a void method --> doNothing() (this line could be dropped, it's the default behavior)

        // when
        crudService.delete(1L);

        // then
        verify(dataService).delete(1L);
    }

    @Test
    void deleteThrowsNotFoundWhenDataServiceThrows() {
        // given
        doThrow(new NotFoundException(TestDomain.class, 2L)).when(dataService).delete(2L); // throw an exception for the void method delete()

        // when, then
        assertThrows(NotFoundException.class, () -> crudService.delete(2L));
        verify(dataService).delete(2L);
    }
}