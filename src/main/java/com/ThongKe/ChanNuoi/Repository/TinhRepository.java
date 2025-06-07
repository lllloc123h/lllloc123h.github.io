package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.Tinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TinhRepository extends JpaRepository<Tinh, Integer> {
    // Add custom query methods here if needed
}
