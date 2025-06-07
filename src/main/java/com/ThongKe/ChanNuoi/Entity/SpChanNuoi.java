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
@Table(name = "sp_chan_nuoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpChanNuoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "ma_sp_chan_nuoi")
    private int maSpChanNuoi;
	
    @Column(name = "ten_sp_chan_nuoi")
    private String tenSpChanNuoi;
	
    @Column(name = "is_gia_cam")
    private boolean isGiaCam;
   





}
