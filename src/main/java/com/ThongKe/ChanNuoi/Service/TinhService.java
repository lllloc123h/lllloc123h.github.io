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
public class TinhService {
	@Autowired
    private TinhRepository tinhRepository;

    public List<Tinh> tinhFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return tinhRepository.findAll(pageable).getContent();
    }
    public Tinh tinhSave(Tinh tinh) {
        return tinhRepository.save(tinh);
    }
    public Optional<Tinh> tinhFindById(int id) {
        return tinhRepository.findById(id);
    }
    public void tinhDeleteById(int id) {
        tinhRepository.deleteById(id);
    }
}