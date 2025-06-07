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
public class XaAPI {
	@Autowired
	private XaService xaService;
	
	@Autowired
	private XaMapper xaMapper;

	@GetMapping("/Xa")
	public ResponseEntity<List<XaDTOS>> getAllXaApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<XaDTOS> xa = new ArrayList<XaDTOS>();
		xaService.xaFindAll(page, size).forEach(e -> {
			xa.add(xaMapper.mapper(e));
		});
		return ResponseEntity.ok(xa);
	}

	@GetMapping("/Xa/{id}")
	public ResponseEntity<Xa> getXaByIdApi(@PathVariable int id) {
		Xa xa =(Xa)xaService.xaFindById(id).orElse(new Xa());
		return ResponseEntity.ok(xa);
	}
	@PostMapping("/Xa")
	public ResponseEntity<Xa> addNewXa(@RequestBody XaDTOS entity) {
	    
	    Xa saved = xaService.xaSave(xaMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Xa")
	public ResponseEntity<Xa> updateXa(@RequestBody Xa entity) {
	    Xa updated = xaService.xaSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Xa/{id}")
	public ResponseEntity<Void> deleteXa(@PathVariable int id) {
	    xaService.xaDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
