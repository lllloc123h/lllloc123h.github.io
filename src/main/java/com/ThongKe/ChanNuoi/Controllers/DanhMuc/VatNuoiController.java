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

import com.ThongKe.ChanNuoi.Entities.DanhMuc.VatNuoi;
import com.ThongKe.ChanNuoi.Repositories.DanhMuc.VatNuoiRepository;

@Controller
@RequestMapping("/VatNuoi")
public class VatNuoiController {
	@Autowired
	VatNuoiRepository VatNuoiRepo;
	@GetMapping
	public String getAllVatNuoi(Model model) {
		List<VatNuoi> list = VatNuoiRepo.findAll();
		VatNuoi vatnuoi = new VatNuoi();
		model.addAttribute("vatnuoi", vatnuoi);
		model.addAttribute("list", list);
		return "/DanhMuc/VatNuoi.html";
	}
	@PostMapping
	public String saveVatNuoi(@ModelAttribute("vatnuoi") VatNuoi vatnuoi) {
		VatNuoiRepo.save(vatnuoi);
		return "redirect:VatNuoi";
	}
	@GetMapping("/update/{id}")
	public String updateVatNuoi(@PathVariable Integer id,Model model) {
		model.addAttribute("vatnuoi", VatNuoiRepo.getReferenceById(id));
		model.addAttribute("list", VatNuoiRepo.findAll());
		return "/DanhMuc/VatNuoi.html";
	}
	@GetMapping("/delete/{id}")
	public String deleteVatNuoi(@PathVariable Integer id) {
		VatNuoiRepo.deleteById(id);
		return "redirect:/VatNuoi";
	}
}
