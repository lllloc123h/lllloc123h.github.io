package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class VatNuoiMapper {
	
	public VatNuoiDTOS mapper(VatNuoi entity) {
		return new VatNuoiDTOS(
				    entity.getId(),
				    entity.getTenVatNuoi(),
				    entity.getMaVatNuoi(),
				    entity.isNhomKhac()
			);
	}
	public VatNuoi mapperToObject(VatNuoiDTOS entity) {
		return new VatNuoi(
					entity.getId(),
					entity.getTenVatNuoi(),
					entity.getMaVatNuoi(),
					entity.isNhomKhac()
			);
	}
	
}
