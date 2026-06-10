package br.com.joaodddev.account_service.repository;

import br.com.joaodddev.account_service.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}