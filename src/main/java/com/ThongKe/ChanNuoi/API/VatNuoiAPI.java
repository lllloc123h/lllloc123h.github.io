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
public class VatNuoiAPI {
	@Autowired
	private VatNuoiService vatNuoiService;
	
	@Autowired
	private VatNuoiMapper vatNuoiMapper;

	@GetMapping("/VatNuoi")
	public ResponseEntity<List<VatNuoiDTOS>> getAllVatNuoiApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<VatNuoiDTOS> vatNuoi = new ArrayList<VatNuoiDTOS>();
		vatNuoiService.vatNuoiFindAll(page, size).forEach(e -> {
			vatNuoi.add(vatNuoiMapper.mapper(e));
		});
		return ResponseEntity.ok(vatNuoi);
	}

	@GetMapping("/VatNuoi/{id}")
	public ResponseEntity<VatNuoi> getVatNuoiByIdApi(@PathVariable int id) {
		VatNuoi vatNuoi =(VatNuoi)vatNuoiService.vatNuoiFindById(id).orElse(new VatNuoi());
		return ResponseEntity.ok(vatNuoi);
	}
	@PostMapping("/VatNuoi")
	public ResponseEntity<VatNuoi> addNewVatNuoi(@RequestBody VatNuoiDTOS entity) {
	    
	    VatNuoi saved = vatNuoiService.vatNuoiSave(vatNuoiMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/VatNuoi")
	public ResponseEntity<VatNuoi> updateVatNuoi(@RequestBody VatNuoi entity) {
	    VatNuoi updated = vatNuoiService.vatNuoiSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/VatNuoi/{id}")
	public ResponseEntity<Void> deleteVatNuoi(@PathVariable int id) {
	    vatNuoiService.vatNuoiDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
