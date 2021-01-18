package com.tusofia.transportify.transport.history.controller;

import com.tusofia.transportify.transport.history.entity.TransportHistory;
import com.tusofia.transportify.transport.history.service.TransportHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/transport-history")
public class TransportHistoryController {
  @Autowired
  private TransportHistoryService transportHistoryService;

  @GetMapping
  public ResponseEntity<List<TransportHistory>> getAllByUserId() {
    return new ResponseEntity<>(this.transportHistoryService.getAllByUserId(), HttpStatus.OK);
  }
}
