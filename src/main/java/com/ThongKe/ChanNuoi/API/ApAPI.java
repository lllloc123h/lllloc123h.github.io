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
public class ApAPI {
	@Autowired
	private ApService apService;
	
	@Autowired
	private ApMapper apMapper;

	@GetMapping("/Ap")
	public ResponseEntity<List<ApDTOS>> getAllApApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ApDTOS> ap = new ArrayList<ApDTOS>();
		apService.apFindAll(page, size).forEach(e -> {
			ap.add(apMapper.mapper(e));
		});
		return ResponseEntity.ok(ap);
	}

	@GetMapping("/Ap/{id}")
	public ResponseEntity<Ap> getApByIdApi(@PathVariable int id) {
		Ap ap =(Ap)apService.apFindById(id).orElse(new Ap());
		return ResponseEntity.ok(ap);
	}
	@PostMapping("/Ap")
	public ResponseEntity<Ap> addNewAp(@RequestBody ApDTOS entity) {
	    
	    Ap saved = apService.apSave(apMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Ap")
	public ResponseEntity<Ap> updateAp(@RequestBody Ap entity) {
	    Ap updated = apService.apSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Ap/{id}")
	public ResponseEntity<Void> deleteAp(@PathVariable int id) {
	    apService.apDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
