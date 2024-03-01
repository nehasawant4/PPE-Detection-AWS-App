package com.ppedetectionapp.controller;

import com.ppedetectionapp.service.S3ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppedetectionapp.entity.FormData;

@Controller
public class UIController {

	@Autowired
    S3ConnectionService s3Service;

	@GetMapping("/")
	public String getDashboard() {
		return "dashboard";
	}

	@GetMapping("/building/grid/{id}")
	public String getBuilding(@PathVariable("id") int id, Model model) {
		model.addAttribute("building_id", id);
		FormData formdata = new FormData();
		formdata.setId(id);
		model.addAttribute("formdata", formdata);
		return "building";
	}

	@GetMapping("/add/floor")
	public String addFloor() {
		return "addfloor";
	}

	@GetMapping("/add/wing")
	public String addWing() {
		return "addwing";
	}

	// http://localhost:5000/upload
	@PostMapping("/upload")
	public String uploadImagesToWing(@ModelAttribute FormData formData) {

		s3Service.updloadImages(formData.getId(), formData.getFloorName(), formData.getWingName(),
				formData.getImages());
		
		return "redirect:/building/grid/" + formData.getId();
		
	}

}
