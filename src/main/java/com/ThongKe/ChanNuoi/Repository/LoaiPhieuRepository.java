package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.LoaiPhieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiPhieuRepository extends JpaRepository<LoaiPhieu, Integer> {
    // Add custom query methods here if needed
}
