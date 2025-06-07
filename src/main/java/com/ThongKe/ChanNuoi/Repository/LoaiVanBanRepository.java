package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.LoaiVanBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiVanBanRepository extends JpaRepository<LoaiVanBan, Integer> {
    // Add custom query methods here if needed
}
