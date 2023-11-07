package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.product.entity.Products;
import com.product.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public String home(Model model) {
		List<Products> products = productRepository.findAll();
		model.addAttribute("all_products", products);
		return "index";
	}

	@GetMapping("/addProduct")
	public String loadForm() {
		return "addProduct";
	}

	@GetMapping("/editProduct/{id}")
	public String editForm(@PathVariable(value="id") long id, Model model) {
		Optional<Products> products = productRepository.findById(id);
		Products product = products.get();
		model.addAttribute("product",product);
		return "editProduct";
	}
	
	@PostMapping("/saveproducts")
	public String saveProducts(@ModelAttribute Products products, HttpSession session) {
		productRepository.save(products);
		session.setAttribute("message", "Product added succesfully");
		return "redirect:/addProduct";
	}
	
	@PostMapping("/updateproducts")
	public String updateproducts(@ModelAttribute Products products, HttpSession session ) {
		productRepository.save(products);
		session.setAttribute("message", "Product edited succesfully");
		return "redirect:/";
	}
	
	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value="id") long id, Model model) {
		productRepository.deleteById(id);
		return "redirect:/";
	}
	
}
