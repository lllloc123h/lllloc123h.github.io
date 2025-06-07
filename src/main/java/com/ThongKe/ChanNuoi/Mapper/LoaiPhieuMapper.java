package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LoaiPhieuMapper {
	
	public LoaiPhieuDTOS mapper(LoaiPhieu entity) {
		return new LoaiPhieuDTOS(
				    entity.getMaPhieu(),
				    entity.getTenPhieu()
			);
	}
	public LoaiPhieu mapperToObject(LoaiPhieuDTOS entity) {
		return new LoaiPhieu(
					entity.getMaPhieu(),
					entity.getTenPhieu()
			);
	}
	
}
