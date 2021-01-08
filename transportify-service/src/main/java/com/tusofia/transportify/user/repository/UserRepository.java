package com.tusofia.transportify.user.repository;

import com.tusofia.transportify.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);
  Optional<User> findByUsername(String username);

  @Query(
          value = "SELECT U.USER_ID, U.FIRST_NAME, U.LAST_NAME, U.ADDITIONAL_DETAILS, U.DATE_OF_BIRTH, " +
                  "U.EMAIL, U.FIRST_NAME, U.GENDER, U.IMAGE_URL, U.IS_ENABLED, U.LAST_NAME, U.PHONE_NUMBER, U.REGISTERED_ON, U.USERNAME, U.PASSWORD, " +
                  "COUNT(DISTINCT D.DRIVE_TRANSPORT_ID) AS DRIVE_TRANSPORT_COUNT, COUNT(DISTINCT R.Id) AS RIDE_TRANSPORT_COUNT " +
                  "FROM USER U " +
                  "LEFT JOIN DRIVE_TRANSPORT D ON D.USER_ID = U.USER_ID " +
                  "LEFT JOIN RIDE_TRANSPORT R ON R.USER_ID = U.USER_ID " +
                  "WHERE U.USERNAME = :username " +
                  "GROUP BY U.USER_ID",
          nativeQuery = true
  )
  User getUserProfileDetailsByUsername(@Param("username") String username);
}
