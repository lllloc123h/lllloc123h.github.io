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
@Table(name = "quy_mo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuyMo {
    @Id
    
	
    @Column(name = "Id")
    private String id;
	
    @Column(name = "quy_mo_name")
    private String quyMoName;
   
    @ManyToOne
    @JoinColumn(name = "vat_nuoi_id")
    private VatNuoi vatNuoi;





}
