package com.ThongKe.ChanNuoi.Controllers.DanhMuc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ThongKe.ChanNuoi.Entities.DanhMuc.Phieu;
import com.ThongKe.ChanNuoi.Repositories.DanhMuc.MaPhieuRepository;

@Controller
@RequestMapping("/LoaiPhieu")
public class PhieuController {
	@Autowired
	MaPhieuRepository MaPhieuRepo;
	
	@GetMapping
	public String getAllPhieu(Model model){
			List<Phieu> listPhieu = MaPhieuRepo.findAll();
			model.addAttribute("ListPhieu",listPhieu);
			model.addAttribute("phieu", new Phieu());
			return "DanhMuc/LoaiPhieu.html";
	}
	@PostMapping
	public String savePhieu(@ModelAttribute("phieu") Phieu phieu) {
		MaPhieuRepo.save(phieu);
		return "redirect:/LoaiPhieu";
	}
	@GetMapping("/delete/{id}")
	public String deletePhieu(@PathVariable int id) {
		MaPhieuRepo.deleteById(id);
		return "redirect:/LoaiPhieu";
	}
	@GetMapping("/update/{id}")
	public String updatePhieu(@PathVariable Integer id, Model model) {
		Phieu phieu = MaPhieuRepo.getReferenceById(id);
		model.addAttribute("phieu",phieu);
		model.addAttribute("ListPhieu", MaPhieuRepo.findAll());
		return "DanhMuc/LoaiPhieu.html";
	}
}
