package com.tusofia.transportify.user.service;

import com.tusofia.transportify.fcm.model.NotificationTypeEnum;
import com.tusofia.transportify.fcm.service.PushNotificationService;
import com.tusofia.transportify.security.SecurityUtils;
import com.tusofia.transportify.user.dto.UserRatingDto;
import com.tusofia.transportify.user.entity.User;
import com.tusofia.transportify.user.entity.UserRating;
import com.tusofia.transportify.user.entity.UserRatingId;
import com.tusofia.transportify.user.exceptions.UserRatingException;
import com.tusofia.transportify.user.repository.IUserRatingRepository;
import com.tusofia.transportify.user.response.UserRatingOccurrenceResponse;
import com.tusofia.transportify.util.CustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRatingService {
  @Autowired
  private IUserRatingRepository userRatingRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private CustomMapper customMapper;

  @Autowired
  private PushNotificationService pushNotificationService;

  public UserRating persist(UserRatingDto userRatingDto, Long raterUserId) {
    if (userRatingDto.getRatedUserId().equals(raterUserId)) {
      throw new UserRatingException("You can't rate yourself!");
    }
    UserRating userRating = this.customMapper.toUserRatingEntity(userRatingDto);
    User ratedUser = this.userService.findUserById(userRatingDto.getRatedUserId());
    User raterUser = this.userService.findUserById(raterUserId);

    UserRatingId userRatingId = new UserRatingId();
    userRatingId.setRatedUser(ratedUser);
    userRatingId.setRaterUser(raterUser);
    userRating.setId(userRatingId);

    UserRating savedUserRating = this.userRatingRepository.save(userRating);


    this.userService.updateCurrentRating(this.findAverageRatingForRatedUser(userRatingDto.getRatedUserId()), userRatingDto.getRatedUserId());

    Map<String, String> data = Map.of("returnUrl", "/tabs/profile/rating", "type", NotificationTypeEnum.USER_RATE.getValue());
    this.pushNotificationService.sendPushNotification(ratedUser.getId(), "You've been rated!", String.format("%s rated you with %s stars", raterUser.getUsername(), userRatingDto.getValue()), data);

    return savedUserRating;
  }

  public List<UserRating> getAllByRatingValue(Long ratedUserId, Integer value) {
    if (ratedUserId == null) {
      User currentUser = SecurityUtils.getCurrentUser();
      ratedUserId = currentUser.getId();
    }
    return this.userRatingRepository.findAllByIdRatedUserIdAndValueOrderByCreatedAt(ratedUserId, value);
  }

  public UserRatingOccurrenceResponse getRatingValueOccurrence(Long ratedUserId) {
    if (ratedUserId == null) {
      User currentUser = SecurityUtils.getCurrentUser();
      ratedUserId = currentUser.getId();
    }
    List<UserRating> userRatings = this.getAllByRatedUserId(ratedUserId);

    return UserRatingOccurrenceResponse.builder()
            .ratingOccurrences(userRatings.stream()
                    .collect(Collectors.groupingBy(UserRating::getValue, Collectors.counting())))
            .userRating(this.findAverageRatingForRatedUser(ratedUserId))
            .build();
  }

  public List<UserRating> getAllByRatedUserId(Long ratedUserId) {
    return this.userRatingRepository.findAllByIdRatedUserId(ratedUserId);
  }

  private Integer findAverageRatingForRatedUser(Long ratedUserId) {
    List<UserRating> ratedUserRatings = this.getAllByRatedUserId(ratedUserId);

    double averageRating = ratedUserRatings.stream()
            .mapToInt(UserRating::getValue)
            .summaryStatistics()
            .getAverage();
    return (int) Math.round(averageRating);
  }
}
