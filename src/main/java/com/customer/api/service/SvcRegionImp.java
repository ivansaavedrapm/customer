package com.customer.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.DtoRegionIn;
import com.customer.api.entity.Region;
import com.customer.api.repository.RepoRegion;
import com.customer.commons.dto.ApiResponse;
import com.customer.exception.ApiException;
import com.customer.exception.DBAccessException;

@Service
public class SvcRegionImp implements SvcRegion{

	@Autowired
	RepoRegion repo;	
	
	@Override
	public List<Region> findAll() {
		try {
			return repo.findAll();
		}catch (DataAccessException e) {
	        throw new DBAccessException();
		}
	}

	@Override
	public List<Region> findActive() {
		try {
			return repo.findByStatusOrderByRegionAsc(1);
		}catch (DataAccessException e) {
	        throw new DBAccessException();
		}
	}

	@Override
	public ApiResponse create(DtoRegionIn in) {
		try {
			repo.create(in.getRegion(), in.getTag());
			return new ApiResponse("La región ha sido registrada");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_region"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la región ya está registrado");
			if (e.getLocalizedMessage().contains("ux_tag"))
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la región ya está registrado");
	        throw new DBAccessException();
		}
	}

	@Override
	public ApiResponse update(DtoRegionIn in, Integer id) {
		try {
			validateId(id);
			repo.update(id, in.getRegion(), in.getTag());
			return new ApiResponse("La región ha sido actualizada");
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_region"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la región ya está registrado");
			if (e.getLocalizedMessage().contains("ux_tag"))
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la región ya está registrado");
	        throw new DBAccessException();
		}
	}

	@Override
	public ApiResponse enable(Integer id) {
		try {
			validateId(id);
			repo.setStatus(id, 1);
			return new ApiResponse("La región ha sido activada");
		}catch (DataAccessException e) {
	        throw new DBAccessException();
		}
	}

	@Override
	public ApiResponse disable(Integer id) {
		try {
			validateId(id);
			repo.setStatus(id, 0);
			return new ApiResponse("La región ha sido desactivada");
		}catch (DataAccessException e) {
	        throw new DBAccessException();
		}
	}

	private void validateId(Integer id) {
		if(repo.findById(id).isEmpty())
			throw new ApiException(HttpStatus.NOT_FOUND, "El id de la región no existe");
	}

}
