package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TinhMapper {
	
	public TinhDTOS mapper(Tinh entity) {
		return new TinhDTOS(
				    entity.getMaTinh(),
				    entity.getTenTinh()
			);
	}
	public Tinh mapperToObject(TinhDTOS entity) {
		return new Tinh(
					entity.getMaTinh(),
					entity.getTenTinh()
			);
	}
	
}
