package com.customer.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.DtoCustomerIn;
import com.customer.api.dto.DtoCustomerListOut;
import com.customer.api.dto.DtoCustomerOut;
import com.customer.api.entity.Customer;
import com.customer.api.entity.CustomerImage;
import com.customer.api.repository.RepoCustomer;
import com.customer.api.repository.RepoCustomerImage;
import com.customer.commons.dto.ApiResponse;
import com.customer.commons.mapper.MapperCustomer;
import com.customer.exception.ApiException;
import com.customer.exception.DBAccessException;

@Service
public class SvcCustomerImp implements SvcCustomer{
	
	@Autowired
	RepoCustomer repo;
	
	@Autowired
	RepoCustomerImage repoCustomerImage;

	@Autowired
	MapperCustomer mapper;
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	@Override
	public List<DtoCustomerListOut> findAll() {
		try {
			List<Customer> customers = repo.findAll();
			return mapper.toListOut(customers);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public DtoCustomerOut get(Integer id) {
		try {
			validateCustomerId(id);
			
			DtoCustomerOut customer = repo.getCustomer(id);
			if(customer == null )
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del cliente no existe");
						
			String image = readCustomerImageFile(id);
			customer.setImage(image);
			
			return customer;

		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse create(DtoCustomerIn in) {
		try {
			Customer customer = mapper.fromDtoIn(in);
			repo.save(customer);
			
			CustomerImage customerImage = new CustomerImage();
			customerImage.setCustomerId(customer.getCustomer_id());
			customerImage.setImage("");
			customerImage.setStatus(1);
				       
			repoCustomerImage.save(customerImage);
			
			return new ApiResponse("El cliente ha sido registrado");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_customer_rfc"))
				throw new ApiException(HttpStatus.CONFLICT, "El rfc del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("ux_customer_mail"))
				throw new ApiException(HttpStatus.CONFLICT, "El mail del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("fk_customer_region"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de región no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse update(Integer id, DtoCustomerIn in) {
		try {
			validateCustomerId(id);
			Customer customer = mapper.fromDtoIn(id, in);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido actualizado");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_customer_rfc"))
				throw new ApiException(HttpStatus.CONFLICT, "El rfc del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("ux_customer_mail"))
				throw new ApiException(HttpStatus.CONFLICT, "El mail del cliente ya está registrado");
			if (e.getLocalizedMessage().contains("fk_customer_region"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de región no existe");
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse enable(Integer id) {
		try {
			validateCustomerId(id);
			Customer customer = repo.findById(id).get();
			customer.setStatus(1);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido activado");
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	@Override
	public ApiResponse disable(Integer id) {
		try {
			validateCustomerId(id);
			Customer customer = repo.findById(id).get();
			customer.setStatus(0);
			repo.save(customer);
			return new ApiResponse("El cliente ha sido desactivado");
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	private void validateCustomerId(Integer id) {
		try {
			if(repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del cliente no existe");
			}
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
	
	private String readCustomerImageFile(Integer customer_id) {
	    try {
		CustomerImage customerImage = repoCustomerImage.findByCustomer_id(customer_id);
		if(customerImage == null)
			return "";
		
		String imageUrl = customerImage.getImage();
		
		// Si la URL comienza con "/" la eliminamos para obtener la ruta relativa
	  	 if (imageUrl.startsWith("/")) {
	       	    imageUrl = imageUrl.substring(1);
	   	}
	  
	  	 // Construir el Path
	  	 Path imagePath = Paths.get(uploadDir, imageUrl);
	  
	  	 // Verifica que el archivo exista
	   	if (!Files.exists(imagePath))
	   	    return "";
	  
		// Leer los bytes de la imagen y codificarlos a Base64
		byte[] imageBytes = Files.readAllBytes(imagePath);
		return Base64.getEncoder().encodeToString(imageBytes);
	    
	    }catch (DataAccessException e) {
	    	throw new DBAccessException(e);
	    }catch (IOException e) {
	    	throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
	    }
	}


}
