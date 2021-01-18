package com.tusofia.transportify.user.repository;

import com.tusofia.transportify.user.entity.UserRating;
import com.tusofia.transportify.user.entity.UserRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRatingRepository extends JpaRepository<UserRating, UserRatingId> {
  List<UserRating> findAllByIdRatedUserId(Long ratedUserId);
  List<UserRating> findAllByIdRatedUserIdAndValueOrderByCreatedAt(Long ratedUserId, Integer value);
}
