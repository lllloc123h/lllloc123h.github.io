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
public class LoaiVanBanService {
	@Autowired
    private LoaiVanBanRepository loaiVanBanRepository;

    public List<LoaiVanBan> loaiVanBanFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return loaiVanBanRepository.findAll(pageable).getContent();
    }
    public LoaiVanBan loaiVanBanSave(LoaiVanBan loaiVanBan) {
        return loaiVanBanRepository.save(loaiVanBan);
    }
    public Optional<LoaiVanBan> loaiVanBanFindById(int id) {
        return loaiVanBanRepository.findById(id);
    }
    public void loaiVanBanDeleteById(int id) {
        loaiVanBanRepository.deleteById(id);
    }
}