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
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.LoaiVanBan;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.LoaiVanbanRepository;
//
//@Controller
//@RequestMapping("/LoaiVanBan")
//public class LoaiVanBancontroller {
//	@Autowired
//	LoaiVanbanRepository LoaiVanBanRepo;
//	
//	@GetMapping
//	public String getAllLoaiVanBan(Model model) {
//		List<LoaiVanBan> list = LoaiVanBanRepo.findAll();
//		LoaiVanBan loaivanban = new LoaiVanBan();
//		model.addAttribute("loaivanban", loaivanban);
//		model.addAttribute("list",list);
//		return"/DanhMuc/LoaiVanBan.html";
//	}
//	
//	@PostMapping
//	public String saveLoaiVanBan(@ModelAttribute("loaivanban") LoaiVanBan loaivanban) {
//		LoaiVanBanRepo.save(loaivanban);
//		return "redirect:/LoaiVanBan";
//	}
//	@GetMapping("/update/{id}")
//	public String updateLoaiVanBan(@PathVariable Integer id,Model model) {
//		model.addAttribute("loaivanban",LoaiVanBanRepo.getReferenceById(id));
//		model.addAttribute("list",LoaiVanBanRepo.findAll());
//		return "/DanhMuc/LoaiVanBan.html";
//	}
//	@GetMapping("/delete/{id}")
//	public String deleteLoaiVanBan(@PathVariable Integer id) {
//		LoaiVanBanRepo.deleteById(id);
//		return "redirect:/LoaiVanBan";
//	}
//}
