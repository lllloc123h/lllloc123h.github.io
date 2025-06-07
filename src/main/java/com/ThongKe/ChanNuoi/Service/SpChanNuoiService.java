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
public class SpChanNuoiService {
	@Autowired
    private SpChanNuoiRepository spChanNuoiRepository;

    public List<SpChanNuoi> spChanNuoiFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return spChanNuoiRepository.findAll(pageable).getContent();
    }
    public SpChanNuoi spChanNuoiSave(SpChanNuoi spChanNuoi) {
        return spChanNuoiRepository.save(spChanNuoi);
    }
    public Optional<SpChanNuoi> spChanNuoiFindById(int id) {
        return spChanNuoiRepository.findById(id);
    }
    public void spChanNuoiDeleteById(int id) {
        spChanNuoiRepository.deleteById(id);
    }
}