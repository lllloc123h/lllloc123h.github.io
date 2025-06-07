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
@Table(name = "xa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Xa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "Ma_xa")
    private int maXa;
	
    @Column(name = "Ten_xa")
    private String tenXa;
	
    @Column(name = "is_Loai")
    private boolean isLoai;
   
    @ManyToOne
    @JoinColumn(name = "Ma_huyen")
    private Huyen huyen;
    @ManyToOne
    @JoinColumn(name = "Ma_tinh")
    private Tinh tinh;





}
