package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.Huyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuyenRepository extends JpaRepository<Huyen, Integer> {
    // Add custom query methods here if needed
}
