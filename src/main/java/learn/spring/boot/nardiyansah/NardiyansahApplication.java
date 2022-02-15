package learn.spring.boot.nardiyansah;

import learn.spring.boot.nardiyansah.domain.Quote;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NardiyansahApplication {

	private static final Logger log = LoggerFactory.getLogger(NardiyansahApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NardiyansahApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) {
		return args -> {
			try {
				LocalTime currentTime = new LocalTime();
				log.info("The current localtime is: " + currentTime);
				Quote quote = restTemplate.getForObject("https://api.quotable.io/random", Quote.class);
				log.info(quote.toString());
			} catch (Exception e) {
				log.error(e.getMessage());
				log.error("get quotes error");
			}
		};
	}

}
