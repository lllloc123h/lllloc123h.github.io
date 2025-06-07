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
@Table(name = "loai_hinh_doanh_nghiep")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiHinhDoanhNghiep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "ma_loai_hinh")
    private int maLoaiHinh;
	
    @Column(name = "ten_loai_hinh")
    private String tenLoaiHinh;
   





}
