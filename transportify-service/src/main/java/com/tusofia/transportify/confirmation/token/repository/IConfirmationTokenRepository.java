package com.tusofia.transportify.confirmation.token.repository;

import com.tusofia.transportify.confirmation.token.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Long> {
  ConfirmationTokenEntity findByConfirmationToken(String confirmationToken);
}
