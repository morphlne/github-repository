package io.pan.github.repository.service;

import io.pan.github.repository.AbstractTest;
import io.pan.github.repository.client.RepositorySource;
import io.pan.github.repository.dto.BranchDTO;
import io.pan.github.repository.dto.CommitDTO;
import io.pan.github.repository.dto.RepositoryDTO;
import io.pan.github.repository.dto.RepositoryOwnerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(RepositoryService.class)
public class RepositoryServiceTest extends AbstractTest {

    @Autowired
    private RepositoryService underTest;

    @MockBean
    private RepositorySource repositorySource;

    @Test
    void getBranches_fewRepositories_runInParallel() {

        VirtualTimeScheduler.getOrSet();

        var fewRepositories = List.of(
                new RepositoryDTO("repo1", false, new RepositoryOwnerDTO("")),
                new RepositoryDTO("repo2", false, new RepositoryOwnerDTO(""))
        );

        var responseDelay = Duration.ofSeconds(10L);

        var delayedResponse = Mono.delay(responseDelay)
                .map(ignore -> List.of(new BranchDTO("", new CommitDTO(""))));

        when(repositorySource.getRepositories(any()))
                .thenReturn(Mono.just(fewRepositories));
        when(repositorySource.getBranches(any(), any()))
                .thenReturn(delayedResponse);

        //for non-parallel calls duration will be at least 2 * responseDelay
        var expectedDuration = responseDelay.plus(Duration.ofSeconds(1L));
        StepVerifier.withVirtualTime(() -> underTest.getRepositories("")
                        .timeout(expectedDuration))
                .thenAwait(expectedDuration)
                .expectNextCount(1)
                .verifyComplete();
        verify(repositorySource, times(2)).getBranches(any(), any());
    }

}
