package com.example.e.commerce.website.Controllers.AdminControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Service.AdminService.AdLoginService;

@RestController
public class AdLoginController {

	@Autowired
	private AdLoginService service;
	
	@GetMapping("/Adshow")
	public ResponseEntity<?> showusers(){
		try {
			List<signUp>log=service.getUsers();
			if(log!=null && !log.isEmpty()) {
				return new ResponseEntity<>(log,HttpStatus.OK);
			}
			return new ResponseEntity<>("No User",HttpStatus.BAD_REQUEST);
	}catch(Exception e) {
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@PutMapping("/Adupdate/{id}")
	public ResponseEntity<?> updateUser(@RequestBody signUp sign,@PathVariable int id){
		try {
			
			boolean stat=service.updateUser(sign, id);
			if(stat) {
				return new ResponseEntity<>(id+" id updated.",HttpStatus.OK);
			}
			return new ResponseEntity<>(id+" Failed to update id.",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/Addelete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id){
		try {
			boolean stat=service.deleteUser( id);
			if(stat) {
				return new ResponseEntity<>(id+" id deleted.",HttpStatus.OK);
			}
			return new ResponseEntity<>(id+" Failed to delete id.",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
