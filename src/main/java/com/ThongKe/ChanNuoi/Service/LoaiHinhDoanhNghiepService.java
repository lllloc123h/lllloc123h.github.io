package com.ThongKe.ChanNuoi.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class LoaiHinhDoanhNghiepService {
	@Autowired
    private LoaiHinhDoanhNghiepRepository loaiHinhDoanhNghiepRepository;

    public List<LoaiHinhDoanhNghiep> loaiHinhDoanhNghiepFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return loaiHinhDoanhNghiepRepository.findAll(pageable).getContent();
    }
    public LoaiHinhDoanhNghiep loaiHinhDoanhNghiepSave(LoaiHinhDoanhNghiep loaiHinhDoanhNghiep) {
        return loaiHinhDoanhNghiepRepository.save(loaiHinhDoanhNghiep);
    }
    public Optional<LoaiHinhDoanhNghiep> loaiHinhDoanhNghiepFindById(int id) {
        return loaiHinhDoanhNghiepRepository.findById(id);
    }
    public void loaiHinhDoanhNghiepDeleteById(int id) {
        loaiHinhDoanhNghiepRepository.deleteById(id);
    }
}