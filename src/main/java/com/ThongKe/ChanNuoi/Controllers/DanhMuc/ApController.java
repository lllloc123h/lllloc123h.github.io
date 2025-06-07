//package com.ThongKe.ChanNuoi.Controllers.DanhMuc;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.ap;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.ApRepository;
//import org.springframework.web.bind.annotation.PostMapping;
//
//
//
//@Controller
//@RequestMapping("/Ap")
//public class ApController {
//@Autowired
//ApRepository ApRepo;
//
//	@GetMapping
//	public String getAllAp(Model model) {
//		model.addAttribute("list", ApRepo.findAll());
//		
//		model.addAttribute("Ap", new ap());
//		return"DanhMuc/Ap";
//	}
//	@PostMapping
//	public String saveAp(@ModelAttribute("Ap") ap Ap) {
//		ApRepo.save(Ap);
//		return "redirect:/Ap";
//	}
//	@GetMapping("/delete/{id}")
//	public String deleteAp(@PathVariable("id") Integer id) {
//		ApRepo.deleteById(id);
//		return "redirect:/Ap";
//	}
//	@GetMapping("/update/{id}")
//	public String updateAp(@PathVariable("id") Integer id, Model model) {
//		model.addAttribute("list",ApRepo.findAll());
//		model.addAttribute("Ap",ApRepo.getReferenceById(id));
//		return"DanhMuc/Ap";
//	}
//	
//}
