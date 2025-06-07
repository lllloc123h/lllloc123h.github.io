package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.LoaiHinhDoanhNghiep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiHinhDoanhNghiepRepository extends JpaRepository<LoaiHinhDoanhNghiep, Integer> {
    // Add custom query methods here if needed
}
