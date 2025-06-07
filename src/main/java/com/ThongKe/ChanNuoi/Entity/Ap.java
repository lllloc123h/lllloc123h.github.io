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
@Table(name = "ap")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "Ma_ap")
    private int maAp;
	
    @Column(name = "Ma_xa")
    private int maXa;
	
    @Column(name = "Ten_ap")
    private String tenAp;
   
    @ManyToOne
    @JoinColumn(name = "Ma_huyen")
    private Huyen huyen;
    @ManyToOne
    @JoinColumn(name = "Ma_tinh")
    private Tinh tinh;





}
