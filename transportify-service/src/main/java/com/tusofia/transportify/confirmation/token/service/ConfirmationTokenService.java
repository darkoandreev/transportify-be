package com.tusofia.transportify.confirmation.token.service;

import com.tusofia.transportify.confirmation.token.entity.ConfirmationTokenEntity;
import com.tusofia.transportify.confirmation.token.repository.IConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
  @Autowired
  private IConfirmationTokenRepository confirmationTokenRepository;

  public ConfirmationTokenEntity persist(ConfirmationTokenEntity confirmationTokenEntity) {
    return this.confirmationTokenRepository.save(confirmationTokenEntity);
  }

  public ConfirmationTokenEntity findByConfirmationToken(String confirmationToken) {
    return this.confirmationTokenRepository.findByConfirmationToken(confirmationToken);
  }
}
