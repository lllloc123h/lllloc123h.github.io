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
public class VatNuoiService {
	@Autowired
    private VatNuoiRepository vatNuoiRepository;

    public List<VatNuoi> vatNuoiFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return vatNuoiRepository.findAll(pageable).getContent();
    }
    public VatNuoi vatNuoiSave(VatNuoi vatNuoi) {
        return vatNuoiRepository.save(vatNuoi);
    }
    public Optional<VatNuoi> vatNuoiFindById(int id) {
        return vatNuoiRepository.findById(id);
    }
    public void vatNuoiDeleteById(int id) {
        vatNuoiRepository.deleteById(id);
    }
}