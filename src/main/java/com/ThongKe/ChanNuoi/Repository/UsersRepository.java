package com.ThongKe.ChanNuoi.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ThongKe.ChanNuoi.Entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByUserName(String userName);
}
