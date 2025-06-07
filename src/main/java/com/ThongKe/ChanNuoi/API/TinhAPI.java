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
public class TinhAPI {
	@Autowired
	private TinhService tinhService;
	
	@Autowired
	private TinhMapper tinhMapper;

	@GetMapping("/Tinh")
	public ResponseEntity<List<TinhDTOS>> getAllTinhApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<TinhDTOS> tinh = new ArrayList<TinhDTOS>();
		tinhService.tinhFindAll(page, size).forEach(e -> {
			tinh.add(tinhMapper.mapper(e));
		});
		return ResponseEntity.ok(tinh);
	}

	@GetMapping("/Tinh/{id}")
	public ResponseEntity<Tinh> getTinhByIdApi(@PathVariable int id) {
		Tinh tinh =(Tinh)tinhService.tinhFindById(id).orElse(new Tinh());
		return ResponseEntity.ok(tinh);
	}
	@PostMapping("/Tinh")
	public ResponseEntity<Tinh> addNewTinh(@RequestBody TinhDTOS entity) {
	    
	    Tinh saved = tinhService.tinhSave(tinhMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Tinh")
	public ResponseEntity<Tinh> updateTinh(@RequestBody Tinh entity) {
	    Tinh updated = tinhService.tinhSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Tinh/{id}")
	public ResponseEntity<Void> deleteTinh(@PathVariable int id) {
	    tinhService.tinhDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
