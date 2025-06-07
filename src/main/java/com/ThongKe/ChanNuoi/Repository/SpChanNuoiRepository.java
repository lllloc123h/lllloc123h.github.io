package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.SpChanNuoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpChanNuoiRepository extends JpaRepository<SpChanNuoi, Integer> {
    // Add custom query methods here if needed
}
