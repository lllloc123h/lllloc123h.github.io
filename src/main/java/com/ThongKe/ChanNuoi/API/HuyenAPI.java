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
public class HuyenAPI {
	@Autowired
	private HuyenService huyenService;
	
	@Autowired
	private HuyenMapper huyenMapper;

	@GetMapping("/Huyen")
	public ResponseEntity<List<HuyenDTOS>> getAllHuyenApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<HuyenDTOS> huyen = new ArrayList<HuyenDTOS>();
		huyenService.huyenFindAll(page, size).forEach(e -> {
			huyen.add(huyenMapper.mapper(e));
		});
		return ResponseEntity.ok(huyen);
	}

	@GetMapping("/Huyen/{id}")
	public ResponseEntity<Huyen> getHuyenByIdApi(@PathVariable int id) {
		Huyen huyen =(Huyen)huyenService.huyenFindById(id).orElse(new Huyen());
		return ResponseEntity.ok(huyen);
	}
	@PostMapping("/Huyen")
	public ResponseEntity<Huyen> addNewHuyen(@RequestBody HuyenDTOS entity) {
	    
	    Huyen saved = huyenService.huyenSave(huyenMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Huyen")
	public ResponseEntity<Huyen> updateHuyen(@RequestBody Huyen entity) {
	    Huyen updated = huyenService.huyenSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Huyen/{id}")
	public ResponseEntity<Void> deleteHuyen(@PathVariable int id) {
	    huyenService.huyenDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
