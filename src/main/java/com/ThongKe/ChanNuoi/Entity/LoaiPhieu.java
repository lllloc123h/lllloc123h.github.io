package com.ThongKe.ChanNuoi.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loai_phieu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiPhieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "ma_phieu")
    private int maPhieu;
	
    @Column(name = "ten_phieu")
    private String tenPhieu;
   





}
