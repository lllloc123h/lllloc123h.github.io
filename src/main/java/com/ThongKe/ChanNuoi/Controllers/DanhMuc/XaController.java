//package com.ThongKe.ChanNuoi.Controllers.DanhMuc;
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
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.Xa;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.XaRepository;
//
//@Controller
//@RequestMapping("/Xa")
//public class XaController {
//	 @Autowired
//	 XaRepository XaRepo;
// 	@GetMapping
// 	public String getAllxa(Model model) {
// 		model.addAttribute("list",XaRepo.findAll());
// 		model.addAttribute("xa",new Xa());
// 		return "DanhMuc/Xa";
// 	}
// 	
// 	@PostMapping
// 	public String saveXa(@ModelAttribute("xa") Xa xa) {
// 		XaRepo.save(xa);
// 		return "redirect:/Xa";
// 	}
// 	
// 	@GetMapping("/delete/{id}")
// 	public String deleteXa(@PathVariable("id") Integer id) {
// 		XaRepo.deleteById(id);
// 		return  "rediect:/Xa";
// 	}
// 	@GetMapping("/update/{id}")
// 	public String updateXa(@PathVariable("id") Integer id,Model model) {
// 		model.addAttribute("list",XaRepo.findAll());
// 		model.addAttribute("xa",XaRepo.getReferenceById(id));
// 		return "DanhMuc/Xa";
// 	}
// 	
//}
