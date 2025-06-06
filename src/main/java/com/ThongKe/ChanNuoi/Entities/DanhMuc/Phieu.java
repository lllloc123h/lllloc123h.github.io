package com.ThongKe.ChanNuoi.Entities.DanhMuc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="loai_phieu")
@Data
@AllArgsConstructor
@NoArgsConstructor	
public class Phieu {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int ma_phieu;
	 
	 private String ten_phieu;
}
