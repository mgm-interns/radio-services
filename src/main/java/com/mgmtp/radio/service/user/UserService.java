package com.mgmtp.radio.service.user;

import com.mgmtp.radio.dto.station.StationDTO;
import com.mgmtp.radio.domain.user.User;
import com.mgmtp.radio.dto.user.UserDTO;
import com.mgmtp.radio.exception.RadioNotFoundException;
import com.mgmtp.radio.sdo.StationPrivacy;
import com.mgmtp.radio.social.google.model.GoogleUser;
import reactor.core.publisher.Flux;
import com.mgmtp.radio.social.facebook.model.FacebookAvatar;
import com.mgmtp.radio.social.facebook.model.FacebookUser;

import java.util.Optional;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDTO getUserByUsername(String username) throws RadioNotFoundException;
    UserDTO getUserByEmail(String email) throws RadioNotFoundException;
    UserDTO register(UserDTO userDTO);
    UserDTO patchUser(String userId, UserDTO userDTO) throws RadioNotFoundException;
    UserDTO patchUserAvatar(String userId, String avatarUrl) throws RadioNotFoundException;
    UserDTO patchUserCover(String userId, String coverUrl) throws RadioNotFoundException;
    UserDTO getUserById(String id) throws RadioNotFoundException;
    User registerByFacebook(FacebookUser facebookUser, FacebookAvatar facebookAvatar);
    Optional<User> getUserByFacebookId(String facebookId);
    User registerByGoogle(GoogleUser googleUser);
    Optional<User> getUserByGoogleId(String googleId);
    List<UserDTO> findUserByUpdatedAt(LocalDate date);
    Flux<StationDTO> getStationByUserId(String userId);
    boolean changePassword(String userId, String oldPassword, String newPassword);
    UserDTO forgotPassword(String email);
    UserDTO resetPassword(String resetPasswordToken, String newPassword);
    Flux<StationDTO> getStationsByUserIdAndPrivacy(String userId, StationPrivacy privacy);
    User getAnonymousUser(String cookieId);
    User getAccessUser(String cookieId);
    void deleteById(String id);
}
