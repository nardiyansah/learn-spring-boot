package learn.spring.boot.nardiyansah;

import learn.spring.boot.nardiyansah.configuration.StorageProperties;
import learn.spring.boot.nardiyansah.domain.Quote;
import learn.spring.boot.nardiyansah.receiver.Receiver;
import learn.spring.boot.nardiyansah.service.StorageService;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class NardiyansahApplication {

	private static final Logger log = LoggerFactory.getLogger(NardiyansahApplication.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(NardiyansahApplication.class, args);

//		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
//		Receiver receiver = ctx.getBean(Receiver.class);
//
//		while (receiver.getCount() == 0) {
//			log.info("Sending message");
//			try {
//				template.convertAndSend("chat", "Hello from Redis");
//			} catch (Exception e) {
//				log.error("cannot send message to redis, maybe you forgot to turn on redis server");
//			}
//		}
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

	@Bean
	public CommandLineRunner initUploadFolder(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer(
			RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter
	) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return  container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Bean
	Receiver receiver() {
		return new Receiver();
	}

	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
}
