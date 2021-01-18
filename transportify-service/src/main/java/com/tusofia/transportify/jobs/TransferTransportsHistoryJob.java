package com.tusofia.transportify.jobs;

import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import com.tusofia.transportify.transport.history.entity.TransportHistory;
import com.tusofia.transportify.transport.history.entity.TransportHistoryDriveDetails;
import com.tusofia.transportify.transport.history.service.TransportHistoryService;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@DisallowConcurrentExecution
public class TransferTransportsHistoryJob implements Job {
  @Autowired
  private DriveTransportService driveTransportService;

  @Autowired
  private RideTransportService rideTransportService;

  @Autowired
  private TransportHistoryService transportHistoryService;

  @Override
  public void execute(JobExecutionContext context) {
    log.info("job executed");
    List<DriveTransportEntity> driveTransportEntities = this.driveTransportService.getAllBeforeTodayDate();

    driveTransportEntities
            .forEach(entity -> {
              if (entity != null) {
                TransportHistory transportHistory = TransportHistory.builder()
                        .cityFrom(entity.getCityFrom())
                        .cityTo(entity.getCityTo())
                        .transportDate(entity.getTransportDate())
                        .isDrive(true)
                        .user(entity.getUser())
                        .build();
                TransportHistoryDriveDetails transportHistoryDriveDetails = TransportHistoryDriveDetails.builder()
                        .additionalDetails(entity.getAdditionalDetails())
                        .transportTime(entity.getTransportTime())
                        .availableSeats(entity.getAvailableSeats())
                        .transportPrice(entity.getTransportPrice())
                        .distance(entity.getDistance())
                        .build();
                transportHistoryDriveDetails.setTransportHistory(transportHistory);
                transportHistory.setTransportHistoryDriveDetails(transportHistoryDriveDetails);
                this.transportHistoryService.persist(transportHistory);
                try {
                  this.driveTransportService.deleteById(entity.getId());
                } catch (NotFoundException e) {
                  log.error("Error while deleting drive transports: {}", e.getMessage());
                }
              }
            });
  }
}
