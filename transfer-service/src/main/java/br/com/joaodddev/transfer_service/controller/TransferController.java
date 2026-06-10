package br.com.joaodddev.transfer_service.controller;

import br.com.joaodddev.transfer_service.dto.TransferRequest;
import br.com.joaodddev.transfer_service.dto.TransferResponse;
import br.com.joaodddev.transfer_service.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@RequestBody @Valid TransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.transfer(request));
    }

    @GetMapping
    public ResponseEntity<List<TransferResponse>> findAll() {
        return ResponseEntity.ok(transferService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(transferService.findById(id));
    }
}
