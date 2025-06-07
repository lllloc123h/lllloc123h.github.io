package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class UsersMapper {
	
	public UsersDTOS mapper(Users entity) {
		return new UsersDTOS(
				    entity.getId(),
				    entity.getUserName(),
				    entity.getPassword(),
				    entity.isAdmin()
			);
	}
	public Users mapperToObject(UsersDTOS entity) {
		return new Users(
					entity.getId(),
					entity.getUserName(),
					entity.getPassword(),
					entity.isAdmin()
			);
	}
	
}
