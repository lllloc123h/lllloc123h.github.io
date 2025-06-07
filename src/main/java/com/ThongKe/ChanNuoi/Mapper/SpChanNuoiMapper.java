package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SpChanNuoiMapper {
	
	public SpChanNuoiDTOS mapper(SpChanNuoi entity) {
		return new SpChanNuoiDTOS(
				    entity.getMaSpChanNuoi(),
				    entity.getTenSpChanNuoi(),
				    entity.isGiaCam()
			);
	}
	public SpChanNuoi mapperToObject(SpChanNuoiDTOS entity) {
		return new SpChanNuoi(
					entity.getMaSpChanNuoi(),
					entity.getTenSpChanNuoi(),
					entity.isGiaCam()
			);
	}
	
}
