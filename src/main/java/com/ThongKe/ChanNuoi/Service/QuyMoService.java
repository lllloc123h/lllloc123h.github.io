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
public class QuyMoService {
	@Autowired
    private QuyMoRepository quyMoRepository;

    public List<QuyMo> quyMoFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return quyMoRepository.findAll(pageable).getContent();
    }
    public QuyMo quyMoSave(QuyMo quyMo) {
        return quyMoRepository.save(quyMo);
    }
    public Optional<QuyMo> quyMoFindById(String id) {
        return quyMoRepository.findById(id);
    }
    public void quyMoDeleteById(String id) {
        quyMoRepository.deleteById(id);
    }
}