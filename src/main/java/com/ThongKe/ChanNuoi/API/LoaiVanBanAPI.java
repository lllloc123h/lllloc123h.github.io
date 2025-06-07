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
public class LoaiVanBanAPI {
	@Autowired
	private LoaiVanBanService loaiVanBanService;
	
	@Autowired
	private LoaiVanBanMapper loaiVanBanMapper;

	@GetMapping("/LoaiVanBan")
	public ResponseEntity<List<LoaiVanBanDTOS>> getAllLoaiVanBanApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<LoaiVanBanDTOS> loaiVanBan = new ArrayList<LoaiVanBanDTOS>();
		loaiVanBanService.loaiVanBanFindAll(page, size).forEach(e -> {
			loaiVanBan.add(loaiVanBanMapper.mapper(e));
		});
		return ResponseEntity.ok(loaiVanBan);
	}

	@GetMapping("/LoaiVanBan/{id}")
	public ResponseEntity<LoaiVanBan> getLoaiVanBanByIdApi(@PathVariable int id) {
		LoaiVanBan loaiVanBan =(LoaiVanBan)loaiVanBanService.loaiVanBanFindById(id).orElse(new LoaiVanBan());
		return ResponseEntity.ok(loaiVanBan);
	}
	@PostMapping("/LoaiVanBan")
	public ResponseEntity<LoaiVanBan> addNewLoaiVanBan(@RequestBody LoaiVanBanDTOS entity) {
	    
	    LoaiVanBan saved = loaiVanBanService.loaiVanBanSave(loaiVanBanMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/LoaiVanBan")
	public ResponseEntity<LoaiVanBan> updateLoaiVanBan(@RequestBody LoaiVanBan entity) {
	    LoaiVanBan updated = loaiVanBanService.loaiVanBanSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/LoaiVanBan/{id}")
	public ResponseEntity<Void> deleteLoaiVanBan(@PathVariable int id) {
	    loaiVanBanService.loaiVanBanDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
