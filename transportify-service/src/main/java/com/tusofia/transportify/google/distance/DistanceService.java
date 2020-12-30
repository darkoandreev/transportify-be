package com.tusofia.transportify.google.distance;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistanceService implements IDistanceService {

  @Override
  public String getDistanceResult(String addressFrom, String addressTo) {
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyCFLqp7dGAeehxj73Ptd5TbTVDRxeIVybU")
            .build();

    String distance = null;

    try {
      DistanceMatrixRow[] distanceMatrixRows = DistanceMatrixApi
              .getDistanceMatrix(context, new String[]{addressFrom}, new String[]{addressTo})
              .mode(TravelMode.DRIVING)
              .await()
              .rows;
      distance = distanceMatrixRows[0].elements[0].distance.humanReadable;
    } catch (Exception ex) {
      log.error("DistanceMatrixApi throws an exception: {}", ex.getMessage());
    }

    return distance;
  }
}
