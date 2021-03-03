package com.tusofia.transportify.jobs;

import com.tusofia.transportify.transport.drive.entity.DriveTransportEntity;
import com.tusofia.transportify.transport.drive.service.DriveTransportService;
import com.tusofia.transportify.transport.history.entity.TransportHistory;
import com.tusofia.transportify.transport.history.entity.TransportHistoryDriveDetails;
import com.tusofia.transportify.transport.history.service.TransportHistoryService;
import com.tusofia.transportify.transport.ride.entity.RideTransportEntity;
import com.tusofia.transportify.transport.ride.service.RideTransportService;
import com.tusofia.transportify.user.entity.User;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
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
    List<RideTransportEntity> rideTransportEntities = this.rideTransportService.getAllBeforeTodayDate();

    driveTransportEntities
            .forEach(entity -> {
              if (entity != null) {
                TransportHistory transportHistory = this.buildTransportHistory(entity.getCityFrom(), entity.getCityTo(), entity.getDistance(), entity.getTransportDate(), entity.getUser(), true);
                TransportHistoryDriveDetails transportHistoryDriveDetails = TransportHistoryDriveDetails.builder()
                        .additionalDetails(entity.getAdditionalDetails())
                        .transportTime(entity.getTransportTime())
                        .availableSeats(entity.getAvailableSeats())
                        .transportPrice(entity.getTransportPrice())
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

    rideTransportEntities
            .forEach(entity -> {
              if (entity != null) {
                TransportHistory transportHistory = this.buildTransportHistory(entity.getCityFrom(), entity.getCityTo(), entity.getDistance(), entity.getTransportDate(), entity.getUser(), false);
                this.transportHistoryService.persist(transportHistory);

                try {
                  this.rideTransportService.deleteById(entity.getId());
                } catch (NotFoundException e) {
                  log.error("Error while deleting ride transports: {}", e.getMessage());
                }
              }
            });
  }

  private TransportHistory buildTransportHistory(String cityFrom, String cityTo, String distance, Date transportDate, User user, boolean isDrive) {
    return TransportHistory.builder()
            .cityFrom(cityFrom)
            .cityTo(cityTo)
            .distance(distance)
            .transportDate(transportDate)
            .isDrive(isDrive)
            .user(user)
            .build();
  }
}
