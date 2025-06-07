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
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.SanPham;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.SanPhamRepository;
//
//@Controller
//@RequestMapping("/SanPham")
//public class SanPhamController {
//	@Autowired
//	SanPhamRepository SanPhamRepo;
//	
//	@GetMapping
//	public String getAllSanPham(Model model) {
//		List<SanPham> list = SanPhamRepo.findAll();
//		model.addAttribute("list", list);
//		model.addAttribute("sanpham",new SanPham());
//		return"/DanhMuc/SanPham.html";
//	}
//	
//	@PostMapping
//	public String saveSanPham(@ModelAttribute("sanpham") SanPham sanPham) {
//		SanPhamRepo.save(sanPham);
//		return "redirect:/SanPham";
//	}
//	
//	@GetMapping("/update/{id}")
//	public String updateSanPham(@PathVariable Integer id,Model model) {
//		model.addAttribute("sanpham",SanPhamRepo.getReferenceById(id));
//		List<SanPham> list = SanPhamRepo.findAll();
//		model.addAttribute("list", list);
//		return "/DanhMuc/SanPham.html";
//	}
//	@GetMapping("/delete/{id}")
//	public String deleteSanPham(@PathVariable Integer id) {
//		SanPhamRepo.deleteById(id);
//		return "redirect:/SanPham";
//	}
//}
