package com.ThongKe.ChanNuoi.Mapper;

import java.time.LocalDateTime;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class LoaiVanBanMapper {
	
	public LoaiVanBanDTOS mapper(LoaiVanBan entity) {
		return new LoaiVanBanDTOS(
				    entity.getMaLoaiVanBan(),
				    entity.getTenMaLoaiVanBan(),
				    entity.getGhiChu()
			);
	}
	public LoaiVanBan mapperToObject(LoaiVanBanDTOS entity) {
		return new LoaiVanBan(
					entity.getMaLoaiVanBan(),
					entity.getTenMaLoaiVanBan(),
					entity.getGhiChu()
			);
	}
	
}
