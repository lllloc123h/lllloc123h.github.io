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
@Table(name = "vat_nuoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatNuoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "Id")
    private int id;
	
    @Column(name = "ten_vat_nuoi")
    private String tenVatNuoi;
	
    @Column(name = "ma_vat_nuoi")
    private String maVatNuoi;
	
    @Column(name = "is_nhom_khac")
    private boolean isNhomKhac;
   





}
