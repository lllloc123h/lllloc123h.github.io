package com.ThongKe.ChanNuoi.Repository;

import com.ThongKe.ChanNuoi.Entity.Ap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApRepository extends JpaRepository<Ap, Integer> {
    // Add custom query methods here if needed
}
