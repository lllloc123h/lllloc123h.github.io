package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.QuyMo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuyMoRepository extends JpaRepository<QuyMo, String> {
    // Add custom query methods here if needed
}
