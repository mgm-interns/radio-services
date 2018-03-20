package com.mgmtp.radio.controller.v1;

import com.mgmtp.radio.controller.BaseRadioController;
import com.mgmtp.radio.controller.response.RadioSuccessResponse;
import com.mgmtp.radio.domain.user.User;
import com.mgmtp.radio.dto.user.UserDTO;
import com.mgmtp.radio.exception.RadioBadRequestException;
import com.mgmtp.radio.exception.RadioException;
import com.mgmtp.radio.exception.RadioNotFoundException;
import com.mgmtp.radio.sdo.CloudinaryDataKeys;
import com.mgmtp.radio.service.user.UserService;
import com.mgmtp.radio.support.CloudinaryHelper;
import com.mgmtp.radio.support.ContentTypeValidator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController extends BaseRadioController {

    public static final String BASE_URL = "/api/v1/users";

    private final UserService userService;
    private final CloudinaryHelper cloudinaryHelper;
    private final ContentTypeValidator contentTypeValidator;

    public UserController(UserService userService, CloudinaryHelper cloudinaryHelper, ContentTypeValidator contentTypeValidator) {
        this.userService = userService;
        this.cloudinaryHelper = cloudinaryHelper;
        this.contentTypeValidator = contentTypeValidator;
    }

    @ApiOperation(
            value = "Get current user",
            notes = "Returns current user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request processed successfully", response = RadioSuccessResponse.class),
            @ApiResponse(code = 500, message = "Server error", response = RadioException.class)
    })
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public RadioSuccessResponse<UserDTO> getUser() throws RadioNotFoundException {
        log.info("GET /api/v1/users/me");

        User currentUser = getCurrentUser();
        UserDTO userDTO = userService.getUserByUsername(currentUser.getUsername());
        return new RadioSuccessResponse<>(userDTO);
    }

    @ApiOperation(
            value = "Register an user",
            notes = "Returns the new user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request processed successfully", response = RadioSuccessResponse.class),
            @ApiResponse(code = 400, message = "Error in request parameters", response = RadioBadRequestException.class),
            @ApiResponse(code = 500, message = "Server error", response = RadioException.class)
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public RadioSuccessResponse<UserDTO> register(@RequestBody UserDTO userDTO) {
        log.info("POST /api/v1/users/register  - data: " + userDTO.toString());

        UserDTO newUserDTO = userService.register(userDTO);
        return new RadioSuccessResponse<>(newUserDTO);
    }

    @ApiOperation(
            value = "Patch the current user",
            notes = "Returns the updated user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request processed successfully", response = RadioSuccessResponse.class),
            @ApiResponse(code = 400, message = "Error in user is not found", response = RadioNotFoundException.class),
            @ApiResponse(code = 500, message = "Server error", response = RadioException.class)
    })
    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public RadioSuccessResponse<UserDTO> patchUser(@RequestBody UserDTO userDTO) throws RadioNotFoundException {
        log.info("PATCH /api/v1/users/me - data: " + userDTO.toString());

        User currentUser = getCurrentUser();
        UserDTO updatedUserDTO = userService.patchUser(currentUser.getUsername(), userDTO);
        return new RadioSuccessResponse<>(updatedUserDTO);
    }

    @ApiOperation(
            value = "Upload and patch current user avatar",
            notes = "Returns the updated user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request processed successfully", response = RadioSuccessResponse.class),
            @ApiResponse(code = 400, message = "Error in user is not found", response = RadioNotFoundException.class),
            @ApiResponse(code = 500, message = "Server error", response = RadioException.class)
    })
    @PatchMapping("/me/avatar")
    @ResponseStatus(HttpStatus.OK)
    public RadioSuccessResponse<UserDTO> patchUserAvatar(@RequestParam("file") MultipartFile multipartFile) throws RadioException, IOException {
        log.info("PATCH /api/v1/users/me/avatar");

        validateFileIsImage(multipartFile);

        Map<String, String> uploadResult = cloudinaryHelper.pushFileToCloud(multipartFile);
        if(uploadResult.get(CloudinaryDataKeys.secure_url.name()) != null) {
            User currentUser = getCurrentUser();
            final String avatarUrl = uploadResult.get(CloudinaryDataKeys.secure_url.name());
            UserDTO updatedUserDTO = userService.patchUserAvatar(currentUser.getUsername(), avatarUrl);
            return new RadioSuccessResponse<>(updatedUserDTO);
        } else {
            throw new RadioException("Can not upload");
        }
    }

    @ApiOperation(
            value = "Upload and patch cover photo of current user",
            notes = "Returns the updated user"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request processed successfully", response = RadioSuccessResponse.class),
            @ApiResponse(code = 400, message = "Error in user is not found", response = RadioNotFoundException.class),
            @ApiResponse(code = 500, message = "Server error", response = RadioException.class)
    })
    @PatchMapping("/me/cover")
    @ResponseStatus(HttpStatus.OK)
    public RadioSuccessResponse<UserDTO> patchCoverPhoto(@RequestParam("file") MultipartFile multipartFile) throws RadioException, IOException {
        log.info("PATCH /api/v1/users/me/cover");

        validateFileIsImage(multipartFile);

        Map<String, String> uploadResult = cloudinaryHelper.pushFileToCloud(multipartFile);
        if(uploadResult.get(CloudinaryDataKeys.secure_url.name()) != null) {
            User currentUser = getCurrentUser();
            final String coverUrl = uploadResult.get(CloudinaryDataKeys.secure_url.name());
            UserDTO updatedUserDTO = userService.patchUserCover(currentUser.getUsername(), coverUrl);
            return new RadioSuccessResponse<>(updatedUserDTO);
        } else {
            throw new RadioException("Can not upload");
        }
    }

    void validateFileIsImage( MultipartFile multipartFile) throws RadioBadRequestException{
        if (multipartFile == null
                || multipartFile.isEmpty()
                || !contentTypeValidator.isImage(multipartFile)) {
            throw new RadioBadRequestException("Bad request");
        }
    }
}
