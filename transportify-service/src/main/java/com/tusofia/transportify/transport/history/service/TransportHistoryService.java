package com.tusofia.transportify.transport.history.service;

import com.tusofia.transportify.security.SecurityUtils;
import com.tusofia.transportify.transport.history.entity.TransportHistory;
import com.tusofia.transportify.transport.history.repository.ITransportHistoryRepository;
import com.tusofia.transportify.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportHistoryService {
  @Autowired
  private ITransportHistoryRepository transportHistoryRepository;

  public TransportHistory persist(TransportHistory transportHistory) {
    return this.transportHistoryRepository.save(transportHistory);
  }

  public List<TransportHistory> getAllByUserId() {
    User user = SecurityUtils.getCurrentUser();
    return this.transportHistoryRepository.findAllByUserId(user.getId());
  }
}
