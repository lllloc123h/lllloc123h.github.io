package com.ThongKe.ChanNuoi.Repositories.DanhMuc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ThongKe.ChanNuoi.Entities.DanhMuc.ap;

@Repository
public interface ApRepository extends JpaRepository<ap, Integer> {

}
