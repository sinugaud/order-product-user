package com.javapedia.service;

import com.javapedia.dto.AuthUser;
import com.javapedia.entity.User;
import com.javapedia.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserInfoService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);


	@Autowired
	private UserInfoRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AuthUser user =userRepository
				.findByUsername(username)
				.map(AuthUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found: " + username));

		return user;

	}
	public String addUser(User userInfo) {
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

		userRepository.save(userInfo);
		return "User Added Successfully";
	}

//	@Autowired
//	private UserInfoRepository repository;
//
//	@Autowired
//	private PasswordEncoder encoder;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		Optional<UserInfo> userDetail = repository.findByName(username);
//
//		// Converting userDetail to UserDetails
//		return userDetail.map(UserInfoDetails::new)
//				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
//	}
//
//	public String addUser(UserInfo userInfo) {
//		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
//		repository.save(userInfo);
//		return "User Added Successfully";
//	}


} 
