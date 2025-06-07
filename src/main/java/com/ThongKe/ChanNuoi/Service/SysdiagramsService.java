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
public class SysdiagramsService {
	@Autowired
    private SysdiagramsRepository sysdiagramsRepository;

    public List<Sysdiagrams> sysdiagramsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return sysdiagramsRepository.findAll(pageable).getContent();
    }
    public Sysdiagrams sysdiagramsSave(Sysdiagrams sysdiagrams) {
        return sysdiagramsRepository.save(sysdiagrams);
    }
    public Optional<Sysdiagrams> sysdiagramsFindById(String id) {
        return sysdiagramsRepository.findById(id);
    }
    public void sysdiagramsDeleteById(String id) {
        sysdiagramsRepository.deleteById(id);
    }
}