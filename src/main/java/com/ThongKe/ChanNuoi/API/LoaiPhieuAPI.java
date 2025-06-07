package com.ThongKe.ChanNuoi.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.ThongKe.ChanNuoi.Entity.*;
import com.ThongKe.ChanNuoi.Service.*;
import com.ThongKe.ChanNuoi.DTOS.*;
import com.ThongKe.ChanNuoi.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class LoaiPhieuAPI {
	@Autowired
	private LoaiPhieuService loaiPhieuService;
	
	@Autowired
	private LoaiPhieuMapper loaiPhieuMapper;

	@GetMapping("/LoaiPhieu")
	public ResponseEntity<List<LoaiPhieuDTOS>> getAllLoaiPhieuApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<LoaiPhieuDTOS> loaiPhieu = new ArrayList<LoaiPhieuDTOS>();
		loaiPhieuService.loaiPhieuFindAll(page, size).forEach(e -> {
			loaiPhieu.add(loaiPhieuMapper.mapper(e));
		});
		return ResponseEntity.ok(loaiPhieu);
	}

	@GetMapping("/LoaiPhieu/{id}")
	public ResponseEntity<LoaiPhieu> getLoaiPhieuByIdApi(@PathVariable int id) {
		LoaiPhieu loaiPhieu =(LoaiPhieu)loaiPhieuService.loaiPhieuFindById(id).orElse(new LoaiPhieu());
		return ResponseEntity.ok(loaiPhieu);
	}
	@PostMapping("/LoaiPhieu")
	public ResponseEntity<LoaiPhieu> addNewLoaiPhieu(@RequestBody LoaiPhieuDTOS entity) {
	    
	    LoaiPhieu saved = loaiPhieuService.loaiPhieuSave(loaiPhieuMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/LoaiPhieu")
	public ResponseEntity<LoaiPhieu> updateLoaiPhieu(@RequestBody LoaiPhieu entity) {
	    LoaiPhieu updated = loaiPhieuService.loaiPhieuSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/LoaiPhieu/{id}")
	public ResponseEntity<Void> deleteLoaiPhieu(@PathVariable int id) {
	    loaiPhieuService.loaiPhieuDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
