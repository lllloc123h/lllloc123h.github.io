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
@Table(name="vat_nuoi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VatNuoi {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int Id;
	
	private String ten_vat_nuoi;
	private String ma_vat_nuoi;
	private boolean nhom_khac;
}
