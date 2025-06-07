package com.ThongKe.ChanNuoi.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ThongKe.ChanNuoi.DTOS.HuyenDTOS;
import com.ThongKe.ChanNuoi.Entity.Huyen;
import com.ThongKe.ChanNuoi.Service.TinhService;

@Component
public class HuyenMapper {
	@Autowired
	private TinhService tinhService;

	public HuyenDTOS mapper(Huyen entity) {
		return new HuyenDTOS(entity.getMaHuyen(), entity.getTenHuyen(), entity.getTinh().getMaTinh());
	}

	public Huyen mapperToObject(HuyenDTOS entity) {
		return new Huyen(entity.getMaHuyen(), entity.getTenHuyen(),
				tinhService.tinhFindById(entity.getTinh()).orElse(null));
	}

}
