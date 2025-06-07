package com.ThongKe.ChanNuoi.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersAPI {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private UsersMapper usersMapper;

	@GetMapping("/Users")
	public ResponseEntity<List<UsersDTOS>> getAllUsersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<UsersDTOS> users = new ArrayList<UsersDTOS>();
		usersService.usersFindAll(page, size).forEach(e -> {
			users.add(usersMapper.mapper(e));
		});
		return ResponseEntity.ok(users);
	}

	@GetMapping("/Users/{id}")
	public ResponseEntity<Users> getUsersByIdApi(@PathVariable int id) {
		Users users =(Users)usersService.usersFindById(id).orElse(new Users());
		return ResponseEntity.ok(users);
	}
	@PostMapping("/Users")
	public ResponseEntity<Users> addNewUsers(@RequestBody UsersDTOS entity) {
	    
	    Users saved = usersService.usersSave(usersMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Users")
	public ResponseEntity<Users> updateUsers(@RequestBody Users entity) {
	    Users updated = usersService.usersSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Users/{id}")
	public ResponseEntity<Void> deleteUsers(@PathVariable int id) {
	    usersService.usersDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
