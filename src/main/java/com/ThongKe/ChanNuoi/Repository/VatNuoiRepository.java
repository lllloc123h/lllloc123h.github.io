package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.VatNuoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatNuoiRepository extends JpaRepository<VatNuoi, Integer> {
    // Add custom query methods here if needed
}
