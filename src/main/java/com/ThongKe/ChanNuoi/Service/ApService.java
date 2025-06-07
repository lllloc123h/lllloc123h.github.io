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
public class ApService {
	@Autowired
    private ApRepository apRepository;

    public List<Ap> apFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return apRepository.findAll(pageable).getContent();
    }
    public Ap apSave(Ap ap) {
        return apRepository.save(ap);
    }
    public Optional<Ap> apFindById(int id) {
        return apRepository.findById(id);
    }
    public void apDeleteById(int id) {
        apRepository.deleteById(id);
    }
}