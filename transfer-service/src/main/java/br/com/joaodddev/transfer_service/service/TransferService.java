package br.com.joaodddev.transfer_service.service;

import br.com.joaodddev.transfer_service.client.AccountClient;
import br.com.joaodddev.transfer_service.client.AccountResponse;
import br.com.joaodddev.transfer_service.domain.Transfer;
import br.com.joaodddev.transfer_service.domain.TransferStatus;
import br.com.joaodddev.transfer_service.dto.TransferRequest;
import br.com.joaodddev.transfer_service.dto.TransferResponse;
import br.com.joaodddev.transfer_service.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final AccountClient accountClient;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        if (transferRepository.existsByIdempotencyKey(request.idempotencyKey())) {
            return TransferResponse.from(
                    transferRepository.findByIdempotencyKey(request.idempotencyKey()).get()
            );
        }

        AccountResponse source = accountClient.findById(request.sourceAccountId());
        AccountResponse target = accountClient.findById(request.targetAccountId());

        if (!source.active()) {
            throw new IllegalArgumentException("Source account is inactive");
        }

        if (!target.active()) {
            throw new IllegalArgumentException("Target account is inactive");
        }

        if (source.balance().compareTo(request.amount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        Transfer transfer = Transfer.builder()
                .idempotencyKey(request.idempotencyKey())
                .sourceAccountId(request.sourceAccountId())
                .targetAccountId(request.targetAccountId())
                .amount(request.amount())
                .status(TransferStatus.PENDING)
                .build();

        transfer = transferRepository.save(transfer);

        accountClient.updateBalance(
                source.id(),
                source.balance().subtract(request.amount())
        );

        accountClient.updateBalance(
                target.id(),
                target.balance().add(request.amount())
        );

        transfer.setStatus(TransferStatus.COMPLETED);
        return TransferResponse.from(transferRepository.save(transfer));
    }

    @Transactional(readOnly = true)
    public List<TransferResponse> findAll() {
        return transferRepository.findAll()
                .stream()
                .map(TransferResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransferResponse findById(UUID id) {
        return TransferResponse.from(
                transferRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Transfer not found"))
        );
    }
}