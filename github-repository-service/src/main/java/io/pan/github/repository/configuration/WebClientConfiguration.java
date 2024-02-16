package io.pan.github.repository.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;

import java.util.List;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient(
            @Value("${github.header.auth}") String auth,
            @Value("${github.header.accept}") String accept,
            @Value("${github.header.apiVersion}") String apiVersion
    ) {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeaders(headers -> {
                    if (StringUtils.isNotBlank(auth)) {
                        headers.setBearerAuth(auth);
                    }
                    headers.setAccept(List.of(MediaType.valueOf(accept)));
                    headers.set("X-GitHub-Api-Version", apiVersion);
                })
                .filter(new LogbookExchangeFilterFunction(jsonLogbook()))
                .build();
    }

    private Logbook jsonLogbook() {
        var formatter = new JsonHttpLogFormatter();
        var writer = new DefaultHttpLogWriter();
        var sink = new DefaultSink(formatter, writer);
        return Logbook.builder()
                .sink(sink)
                .build();
    }

}
