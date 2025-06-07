package com.ThongKe.ChanNuoi.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ThongKe.ChanNuoi.DTOS.XaDTOS;
import com.ThongKe.ChanNuoi.Entity.Xa;
import com.ThongKe.ChanNuoi.Service.HuyenService;
import com.ThongKe.ChanNuoi.Service.TinhService;

@Component
public class XaMapper {
	@Autowired
	private HuyenService huyenService;
	@Autowired
	private TinhService tinhService;

	public XaDTOS mapper(Xa entity) {
		return new XaDTOS(entity.getMaXa(), entity.getTenXa(), entity.isLoai(), entity.getHuyen().getMaHuyen(),
				entity.getTinh().getMaTinh());
	}

	public Xa mapperToObject(XaDTOS entity) {
		return new Xa(entity.getMaXa(), entity.getTenXa(), entity.isLoai(),
				huyenService.huyenFindById(entity.getHuyen()).orElse(null),
				tinhService.tinhFindById(entity.getTinh()).orElse(null));
	}

}
