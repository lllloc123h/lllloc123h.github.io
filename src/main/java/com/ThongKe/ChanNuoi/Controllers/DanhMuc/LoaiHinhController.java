//package com.ThongKe.ChanNuoi.Controllers.DanhMuc;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.LoaiHinh;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.LoaiHinhRepository;
//
//@Controller
//@RequestMapping("/LoaiHinh")
//public class LoaiHinhController {
//@Autowired
//LoaiHinhRepository LoaiHinhRepo;
//	@GetMapping
//	public String getAllLoaiHinh(Model model) {
//		List<LoaiHinh> list = LoaiHinhRepo.findAll();
//		LoaiHinh loaihinh = new LoaiHinh();
//		model.addAttribute("list",list);
//		model.addAttribute("loaihinh",loaihinh);
//		return "/DanhMuc/loaihinh.html";
//	}
//	@PostMapping
//	public String saveLoaiHinh(@ModelAttribute("loaihinh") LoaiHinh loaihinh) {
//		LoaiHinhRepo.save(loaihinh);
//		return "redirect:/LoaiHinh";
//	}
//	
//	@GetMapping("/update/{id}")
//	public String updateLoaiHinh(Model model,@PathVariable Integer id) {
//		LoaiHinh loaihinh = LoaiHinhRepo.getReferenceById(id);
//		model.addAttribute("loaihinh",loaihinh);
//		model.addAttribute("list",LoaiHinhRepo.findAll());
//		return "/DanhMuc/loaihinh.html";
//	}
//	@GetMapping("/delete/{id}")
//	public String deleteLoaihinh(@PathVariable Integer id) {
//		LoaiHinhRepo.deleteById(id);
//		return "redirect:/LoaiHinh";
//	}
//
//}
