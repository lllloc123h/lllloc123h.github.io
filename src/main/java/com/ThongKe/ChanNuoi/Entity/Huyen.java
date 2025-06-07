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
@Table(name = "huyen")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Huyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "ma_huyen")
    private int maHuyen;
	
    @Column(name = "ten_huyen")
    private String tenHuyen;
   
    @ManyToOne
    @JoinColumn(name = "ma_tinh")
    private Tinh tinh;





}
