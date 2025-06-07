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
public class LoaiPhieuService {
	@Autowired
    private LoaiPhieuRepository loaiPhieuRepository;

    public List<LoaiPhieu> loaiPhieuFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return loaiPhieuRepository.findAll(pageable).getContent();
    }
    public LoaiPhieu loaiPhieuSave(LoaiPhieu loaiPhieu) {
        return loaiPhieuRepository.save(loaiPhieu);
    }
    public Optional<LoaiPhieu> loaiPhieuFindById(int id) {
        return loaiPhieuRepository.findById(id);
    }
    public void loaiPhieuDeleteById(int id) {
        loaiPhieuRepository.deleteById(id);
    }
}