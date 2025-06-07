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
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.Huyen;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.HuyenRepository;
//
//@Controller
//@RequestMapping("/Huyen")
//public class HuyenController {
//
//	@Autowired
//	HuyenRepository HuyenRepo;
//
//	// Khi vào đường dẫn /Huyen (từ sidebar hoặc mặc định)
//	@GetMapping
//	public String getAllHuyen(Model model) {
//		List<Huyen> list = HuyenRepo.findAll();
//		model.addAttribute("list", list);
//		model.addAttribute("huyen", new Huyen());
//		return "DanhMuc/Huyen"; // Trả về template index.html làm layout chính
//	}
//
//	@PostMapping
//	public String saveHuyen(@ModelAttribute("huyen") Huyen huyen) {
//		HuyenRepo.save(huyen);
//		return "redirect:/Huyen"; // Chuyển hướng để tải lại trang với dữ liệu mới
//	}
//
//	@GetMapping("/update/{id}")
//	public String updateHuyen(@PathVariable Integer id, Model model) {
//		List<Huyen> list = HuyenRepo.findAll();
//		model.addAttribute("list", list);
//		Huyen huyen = HuyenRepo.getReferenceById(id);
//		model.addAttribute("huyen", huyen);
//		return "DanhMuc/Huyen"; // Trả về template index.html làm layout chính
//	}
//
//	@GetMapping("/delete/{id}")
//	public String deleteHuyen(@PathVariable Integer id) {
//		HuyenRepo.deleteById(id);
//		return "redirect:/Huyen"; // Chuyển hướng để tải lại trang sau khi xóa
//	}
//}