package com.ThongKe.ChanNuoi.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ThongKe.ChanNuoi.DTOS.ApDTOS;
import com.ThongKe.ChanNuoi.Entity.Ap;
import com.ThongKe.ChanNuoi.Service.HuyenService;
import com.ThongKe.ChanNuoi.Service.TinhService;

@Component
public class ApMapper {
	@Autowired
	private HuyenService huyenService;
	@Autowired
	private TinhService tinhService;

	public ApDTOS mapper(Ap entity) {
		return new ApDTOS(entity.getMaAp(), entity.getMaXa(), entity.getTenAp(), entity.getHuyen().getMaHuyen(),
				entity.getTinh().getMaTinh());
	}

	public Ap mapperToObject(ApDTOS entity) {
		return new Ap(entity.getMaAp(), entity.getMaXa(), entity.getTenAp(),
				huyenService.huyenFindById(entity.getHuyen()).orElse(null),
				tinhService.tinhFindById(entity.getTinh()).orElse(null));
	}

}
