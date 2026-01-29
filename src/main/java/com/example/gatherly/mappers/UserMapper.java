package com.example.gatherly.mappers;

import com.example.gatherly.dtos.UserAdditionalData;
import com.example.gatherly.dtos.UserDto;
import com.example.gatherly.entity.User;
import com.example.gatherly.utils.ObjectMapperUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
   UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


   @Mapping(source = "phone", target = "phoneNumber")
   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void userDtoToUser(UserDto userDto, @MappingTarget User user);

   @Mapping(target = "phone", source = "phoneNumber")
   @Mapping(target = "password", ignore = true)
   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   UserDto userToUserDto(User user);

   void updateUserAdditionalDataFromDto(UserDto userDto, @MappingTarget UserAdditionalData userAdditionalData);

   void updateUserDtoFromAdditionalData(@MappingTarget UserDto userDto,  UserAdditionalData userAdditionalData);

   @AfterMapping
  default void enrichWithAdditionalData(
      User user,
      @MappingTarget UserDto userDto
   ) {
      if (user.getAdditionalData() == null) {
         return;
      }

      UserAdditionalData additionalData =
         ObjectMapperUtils.convertJsonToObjectWithDefault(
            user.getAdditionalData(),
            UserAdditionalData.class
         );

      updateUserDtoFromAdditionalData(userDto, additionalData);

   }
}
