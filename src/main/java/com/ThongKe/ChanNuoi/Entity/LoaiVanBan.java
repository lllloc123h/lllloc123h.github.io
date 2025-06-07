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
@Table(name = "loai_van_ban")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiVanBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "ma_loai_van_ban")
    private int maLoaiVanBan;
	
    @Column(name = "ten_ma_loai_van_ban")
    private String tenMaLoaiVanBan;
	
    @Column(name = "ghi_chu")
    private String ghiChu;
   





}
