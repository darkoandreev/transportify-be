package com.tusofia.transportify.transport.history.repository;

import com.tusofia.transportify.transport.history.entity.TransportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransportHistoryRepository extends JpaRepository<TransportHistory, Long> {
  List<TransportHistory> findAllByUserId(Long userId);
}
