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
public class LoaiHinhDoanhNghiepAPI {
	@Autowired
	private LoaiHinhDoanhNghiepService loaiHinhDoanhNghiepService;
	
	@Autowired
	private LoaiHinhDoanhNghiepMapper loaiHinhDoanhNghiepMapper;

	@GetMapping("/LoaiHinhDoanhNghiep")
	public ResponseEntity<List<LoaiHinhDoanhNghiepDTOS>> getAllLoaiHinhDoanhNghiepApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<LoaiHinhDoanhNghiepDTOS> loaiHinhDoanhNghiep = new ArrayList<LoaiHinhDoanhNghiepDTOS>();
		loaiHinhDoanhNghiepService.loaiHinhDoanhNghiepFindAll(page, size).forEach(e -> {
			loaiHinhDoanhNghiep.add(loaiHinhDoanhNghiepMapper.mapper(e));
		});
		return ResponseEntity.ok(loaiHinhDoanhNghiep);
	}

	@GetMapping("/LoaiHinhDoanhNghiep/{id}")
	public ResponseEntity<LoaiHinhDoanhNghiep> getLoaiHinhDoanhNghiepByIdApi(@PathVariable int id) {
		LoaiHinhDoanhNghiep loaiHinhDoanhNghiep =(LoaiHinhDoanhNghiep)loaiHinhDoanhNghiepService.loaiHinhDoanhNghiepFindById(id).orElse(new LoaiHinhDoanhNghiep());
		return ResponseEntity.ok(loaiHinhDoanhNghiep);
	}
	@PostMapping("/LoaiHinhDoanhNghiep")
	public ResponseEntity<LoaiHinhDoanhNghiep> addNewLoaiHinhDoanhNghiep(@RequestBody LoaiHinhDoanhNghiepDTOS entity) {
	    
	    LoaiHinhDoanhNghiep saved = loaiHinhDoanhNghiepService.loaiHinhDoanhNghiepSave(loaiHinhDoanhNghiepMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/LoaiHinhDoanhNghiep")
	public ResponseEntity<LoaiHinhDoanhNghiep> updateLoaiHinhDoanhNghiep(@RequestBody LoaiHinhDoanhNghiep entity) {
	    LoaiHinhDoanhNghiep updated = loaiHinhDoanhNghiepService.loaiHinhDoanhNghiepSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/LoaiHinhDoanhNghiep/{id}")
	public ResponseEntity<Void> deleteLoaiHinhDoanhNghiep(@PathVariable int id) {
	    loaiHinhDoanhNghiepService.loaiHinhDoanhNghiepDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
