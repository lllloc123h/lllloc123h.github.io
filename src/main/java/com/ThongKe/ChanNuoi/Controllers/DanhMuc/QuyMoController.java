//package com.ThongKe.ChanNuoi.Controllers.DanhMuc;
//
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
//import com.ThongKe.ChanNuoi.Entities.DanhMuc.QuyMo;
//import com.ThongKe.ChanNuoi.Repositories.DanhMuc.QuyMoRepository;
//
//@Controller
//@RequestMapping("/QuyMo")
//public class QuyMoController {
//	@Autowired
//	QuyMoRepository QuyMoRepo;
//	
//	@GetMapping
//	public String GetAllQuymo(Model model) {
//		model.addAttribute("list", QuyMoRepo.findAll());
//		model.addAttribute("QuyMo", new QuyMo());
//		return "DanhMuc/QuyMo";
//	}
//	
//	@PostMapping
//	public String saveQuyMo(@ModelAttribute("QuyMo") QuyMo quymo) {
//		QuyMoRepo.save(quymo);
//		return "redirect:/QuyMo";
//	}
//	 
//	@GetMapping("/update/{id}")
//	public String updateQuyMo(@PathVariable("id") String id, Model model) {
//		model.addAttribute("list", QuyMoRepo.findAll());
//		model.addAttribute("QuyMo", QuyMoRepo.getReferenceById(id));
//		return"DanhMuc/QuyMo";
//	}
//	
//	@GetMapping("/delete/{id}")
//	public String deleteQuyMo(@PathVariable("id") String id) {
//		QuyMoRepo.deleteById(id);
//		return "redirect:/QuyMo";
//		
//	}
//	
//}
