CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE transfers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key UUID NOT NULL UNIQUE,
    source_account_id UUID NOT NULL,
    target_account_id UUID NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transfers_idempotency_key ON transfers(idempotency_key);
CREATE INDEX idx_transfers_source_account ON transfers(source_account_id);
CREATE INDEX idx_transfers_target_account ON transfers(target_account_id);