package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.User;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDto, User.class);
        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        repository.save(user);

        return mapper.map(user, UserDto.class);
    }

    public ResponseUser getUserInfo(String userId) {
        User user = repository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저입니다."));

        final ResponseUser responseUser = modelMapper.map(user, ResponseUser.class);

        List<ResponseOrder> orders = new ArrayList<>();
        responseUser.setOrders(orders);

        return responseUser;
    }

    public List<ResponseUser> getAllUserInfo() {
        return repository.findAll().stream()
            .map(user -> modelMapper.map(user, ResponseUser.class))
            .collect(Collectors.toList());
    }
}