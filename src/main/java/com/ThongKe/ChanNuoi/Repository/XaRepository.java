package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.Xa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XaRepository extends JpaRepository<Xa, Integer> {
    // Add custom query methods here if needed
}
