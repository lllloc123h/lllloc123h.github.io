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
public class HuyenService {
	@Autowired
    private HuyenRepository huyenRepository;

    public List<Huyen> huyenFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return huyenRepository.findAll(pageable).getContent();
    }
    public Huyen huyenSave(Huyen huyen) {
        return huyenRepository.save(huyen);
    }
    public Optional<Huyen> huyenFindById(int id) {
        return huyenRepository.findById(id);
    }
    public void huyenDeleteById(int id) {
        huyenRepository.deleteById(id);
    }
}