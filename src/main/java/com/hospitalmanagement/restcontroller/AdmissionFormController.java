package com.hospitalmanagement.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospitalmanagement.exception.RecordNotFoundException;
import com.hospitalmanagement.model.AdmissionForm;
import com.hospitalmanagement.service.AdmissionFormService;

@RestController
@RequestMapping(ConstValue.BASE_URL + "/admissionForm")
public class AdmissionFormController {
	
	@Autowired
	private AdmissionFormService service;
	
	@GetMapping("/all")
	public ResponseEntity<List<AdmissionForm>> list()
	{
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<AdmissionForm>> page(
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, 
			@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "true") Boolean asc)
	{
		Sort sort = asc ? Sort.by(Order.asc(orderBy)) : Sort.by(Order.desc(orderBy));
		Page<AdmissionForm> result = service.findAll(page, size, sort);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("findById/{id}")
	public ResponseEntity<AdmissionForm> findById(@PathVariable Long id)
	{
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AdmissionForm> create(@RequestBody AdmissionForm admissionForm)
	{
		return new ResponseEntity<>(service.insert(admissionForm), HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<AdmissionForm> update(@RequestBody AdmissionForm admissionForm)
	{
		return new ResponseEntity<>(service.update(admissionForm), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id)
	{
		return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
	}
	
}
