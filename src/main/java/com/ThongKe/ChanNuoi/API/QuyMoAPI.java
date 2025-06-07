package com.ThongKe.ChanNuoi.API;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ThongKe.ChanNuoi.DTOS.QuyMoDTOS;
import com.ThongKe.ChanNuoi.Entity.QuyMo;
import com.ThongKe.ChanNuoi.Mapper.QuyMoMapper;
import com.ThongKe.ChanNuoi.Service.QuyMoService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class QuyMoAPI {
	@Autowired
	private QuyMoService quyMoService;

	@Autowired
	private QuyMoMapper quyMoMapper;

	@GetMapping("/QuyMo")
	public ResponseEntity<List<QuyMoDTOS>> getAllQuyMoApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<QuyMoDTOS> quyMo = new ArrayList<QuyMoDTOS>();
		quyMoService.quyMoFindAll(page, size).forEach(e -> {
			quyMo.add(quyMoMapper.mapper(e));
		});
		return ResponseEntity.ok(quyMo);
	}

	@GetMapping("/QuyMo/{id}")
	public ResponseEntity<QuyMo> getQuyMoByIdApi(@PathVariable String id) {
		QuyMo quyMo = (QuyMo) quyMoService.quyMoFindById(id).orElse(new QuyMo());
		return ResponseEntity.ok(quyMo);
	}

	@PostMapping("/QuyMo")
	public ResponseEntity<QuyMo> addNewQuyMo(@RequestBody QuyMoDTOS entity) {

		QuyMo saved = quyMoService.quyMoSave(quyMoMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/QuyMo")
	public ResponseEntity<QuyMo> updateQuyMo(@RequestBody QuyMo entity) {
		QuyMo updated = quyMoService.quyMoSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/QuyMo/{id}")
	public ResponseEntity<Void> deleteQuyMo(@PathVariable String id) {
		quyMoService.quyMoDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
