package br.com.joaodddev.account_service;

import br.com.joaodddev.account_service.dto.AccountRequest;
import br.com.joaodddev.account_service.dto.AccountResponse;
import br.com.joaodddev.account_service.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
class AccountServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("accounts")
            .withUsername("postgres")
            .withPassword("postgres");

    @Container
    static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.0")
    );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateAccountSuccessfully() {
        AccountRequest request = new AccountRequest("João Silva", "12345678901");

        AccountResponse response = accountService.create(request);

        assertThat(response.id()).isNotNull();
        assertThat(response.ownerName()).isEqualTo("João Silva");
        assertThat(response.cpf()).isEqualTo("12345678901");
        assertThat(response.balance()).isEqualByComparingTo("0.00");
        assertThat(response.active()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        AccountRequest request = new AccountRequest("Maria Souza", "98765432100");
        accountService.create(request);

        assertThatThrownBy(() -> accountService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("CPF already registered");
    }

    @Test
    void shouldFindAccountById() {
        AccountRequest request = new AccountRequest("Carlos Lima", "11122233300");
        AccountResponse created = accountService.create(request);

        AccountResponse found = accountService.findById(created.id());

        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.ownerName()).isEqualTo("Carlos Lima");
    }
}