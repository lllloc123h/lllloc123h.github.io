package com.ThongKe.ChanNuoi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ThongKe.ChanNuoi.Entity.Users;
import com.ThongKe.ChanNuoi.Repository.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	public List<Users> usersFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return usersRepository.findAll(pageable).getContent();
	}

	public Users usersSave(Users users) {
		return usersRepository.save(users);
	}

	public Optional<Users> usersFindByUserName(String UserName) {

		return usersRepository.findByUserName(UserName);
	}

	public Optional<Users> usersFindById(int id) {
		return usersRepository.findById(id);
	}

	public void usersDeleteById(int id) {
		usersRepository.deleteById(id);
	}
}