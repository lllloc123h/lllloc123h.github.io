package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class QuyMoMapper {
	@Autowired
	private VatNuoiService vatNuoiService;
	
	public QuyMoDTOS mapper(QuyMo entity) {
		return new QuyMoDTOS(
				    entity.getId(),
				    entity.getQuyMoName(),
				    entity.getVatNuoi().getId()
			);
	}
	public QuyMo mapperToObject(QuyMoDTOS entity) {
		return new QuyMo(
					entity.getId(),
					entity.getQuyMoName(),
					vatNuoiService.vatNuoiFindById(entity.getVatNuoi()).orElse(null)
			);
	}
	
}
