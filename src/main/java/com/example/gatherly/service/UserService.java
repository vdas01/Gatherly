package com.example.gatherly.service;

import com.example.gatherly.dtos.UserAdditionalData;
import com.example.gatherly.dtos.UserDto;
import com.example.gatherly.entity.User;
import com.example.gatherly.enums.UserStatus;
import com.example.gatherly.mappers.UserMapper;
import com.example.gatherly.repository.UserRepository;
import com.example.gatherly.utils.ObjectMapperUtils;
import com.example.gatherly.utils.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;


   public UserDto getUserByUserName(String userName) {
     return userRepository.findByUserName(userName)
         .map(UserMapper.INSTANCE::userToUserDto)
         .orElseThrow(() -> new RuntimeException("No user found with username," + userName));
   }


   public UserDto createUser(UserDto userDto,Boolean isUpdateUser) throws JsonProcessingException {
      Optional<User> userOptional = userRepository.findByUserName(userDto.getUserName());
      if(userOptional.isPresent() && Boolean.FALSE.equals(isUpdateUser)) {
         throw new RuntimeException("Username is not unique");
      }
      User user = userOptional.orElseGet(User::new);
      UserAdditionalData userAdditionalData = ObjectMapperUtils.convertJsonToObjectWithDefault(user.getAdditionalData(), UserAdditionalData.class);

      UserMapper.INSTANCE.updateUserAdditionalDataFromDto(userDto, userAdditionalData);
      UserMapper.INSTANCE.userDtoToUser(userDto,user);
      user.setPassword(SecurityUtil.encode(user.getPassword()));
      user.setAdditionalData(ObjectMapperUtils.convertDtoToJson(userAdditionalData));
      if(Boolean.FALSE.equals(isUpdateUser) || Objects.isNull(userDto.getUserStatus())) {
         user.setUserStatus(UserStatus.ACTIVE);
      }
      return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
   }

   public String deleteUser(String username) {
      User user = userRepository.findByUserName(username)
         .orElseThrow(() -> new RuntimeException("Username not found"));
      userRepository.delete(user);
      return "User has been deleted";
   }

   public UserDto login(String username, String password) {
      Optional<User> userOptional = userRepository.findByUserName(username);
      if(userOptional.isPresent()) {
         User user = userOptional.get();
         if(SecurityUtil.matches(password,user.getPassword())) {
            return UserMapper.INSTANCE.userToUserDto(user);
         }
      }
      throw new RuntimeException("Bad Credentials");
   }
}
