package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LoaiHinhDoanhNghiepMapper {
	
	public LoaiHinhDoanhNghiepDTOS mapper(LoaiHinhDoanhNghiep entity) {
		return new LoaiHinhDoanhNghiepDTOS(
				    entity.getMaLoaiHinh(),
				    entity.getTenLoaiHinh()
			);
	}
	public LoaiHinhDoanhNghiep mapperToObject(LoaiHinhDoanhNghiepDTOS entity) {
		return new LoaiHinhDoanhNghiep(
					entity.getMaLoaiHinh(),
					entity.getTenLoaiHinh()
			);
	}
	
}
