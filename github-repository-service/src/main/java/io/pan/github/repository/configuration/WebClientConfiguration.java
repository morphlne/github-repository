package io.pan.github.repository.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
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
