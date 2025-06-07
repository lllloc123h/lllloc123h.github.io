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
public class SpChanNuoiAPI {
	@Autowired
	private SpChanNuoiService spChanNuoiService;
	
	@Autowired
	private SpChanNuoiMapper spChanNuoiMapper;

	@GetMapping("/SpChanNuoi")
	public ResponseEntity<List<SpChanNuoiDTOS>> getAllSpChanNuoiApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<SpChanNuoiDTOS> spChanNuoi = new ArrayList<SpChanNuoiDTOS>();
		spChanNuoiService.spChanNuoiFindAll(page, size).forEach(e -> {
			spChanNuoi.add(spChanNuoiMapper.mapper(e));
		});
		return ResponseEntity.ok(spChanNuoi);
	}

	@GetMapping("/SpChanNuoi/{id}")
	public ResponseEntity<SpChanNuoi> getSpChanNuoiByIdApi(@PathVariable int id) {
		SpChanNuoi spChanNuoi =(SpChanNuoi)spChanNuoiService.spChanNuoiFindById(id).orElse(new SpChanNuoi());
		return ResponseEntity.ok(spChanNuoi);
	}
	@PostMapping("/SpChanNuoi")
	public ResponseEntity<SpChanNuoi> addNewSpChanNuoi(@RequestBody SpChanNuoiDTOS entity) {
	    
	    SpChanNuoi saved = spChanNuoiService.spChanNuoiSave(spChanNuoiMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/SpChanNuoi")
	public ResponseEntity<SpChanNuoi> updateSpChanNuoi(@RequestBody SpChanNuoi entity) {
	    SpChanNuoi updated = spChanNuoiService.spChanNuoiSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/SpChanNuoi/{id}")
	public ResponseEntity<Void> deleteSpChanNuoi(@PathVariable int id) {
	    spChanNuoiService.spChanNuoiDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
