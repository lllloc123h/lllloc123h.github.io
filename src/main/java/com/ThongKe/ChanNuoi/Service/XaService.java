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
public class XaService {
	@Autowired
    private XaRepository xaRepository;

    public List<Xa> xaFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return xaRepository.findAll(pageable).getContent();
    }
    public Xa xaSave(Xa xa) {
        return xaRepository.save(xa);
    }
    public Optional<Xa> xaFindById(int id) {
        return xaRepository.findById(id);
    }
    public void xaDeleteById(int id) {
        xaRepository.deleteById(id);
    }
}