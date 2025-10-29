package com.customer.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.DtoCustomerImageIn;
import com.customer.api.entity.CustomerImage;
import com.customer.api.repository.RepoCustomerImage;
import com.customer.commons.dto.ApiResponse;
import com.customer.exception.ApiException;
import com.customer.exception.DBAccessException;

@Service
public class SvcCustomerImageImp implements SvcCustomerImage {

	@Autowired
	RepoCustomerImage repo;
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	@Override
	public ApiResponse upload(DtoCustomerImageIn in) {
		try {
			// 0 - Validar formato del Base64
			// Eliminar el prefijo "data:image/png;base64," si existe
			if (in.getImage().startsWith("data:image")) {
				int commaIndex = in.getImage().indexOf(",");
					if (commaIndex != -1) {
				in.setImage(in.getImage().substring(commaIndex + 1));
				}
			}

		    // 1 - Decodificar imagen
			
			// Decodifica la cadena Base64 a bytes
			byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

			// Genera un nombre único para la imagen (se asume extensión PNG)
			String fileName = UUID.randomUUID().toString() + ".png";

			// Construye la ruta completa donde se guardará la imagen
			Path imagePath = Paths.get(uploadDir, "img", "customer", fileName);
			
			
			// 2 - Guardar el File en el sistema de archivos
			
			// Asegurarse de que el directorio exista
			Files.createDirectories(imagePath.getParent());

			// Escribir el archivo en el sistema de archivos
			Files.write(imagePath, imageBytes);
			
			
			// 3 - Guardar la ruta en la base de datos
			
			// Crear la entidad CustomerImage y guardar la URL en la base de datos
			CustomerImage customerImage = new CustomerImage();
			customerImage.setCustomerId(in.getCustomerId());
			customerImage.setImage("/img/customer/" + fileName);
			customerImage.setStatus(1); 

			// Guardar la ruta de la imagen
			repo.save(customerImage);
			
		    return new ApiResponse("La imagen del cliente ha sido actualizada");
		}catch (DataAccessException e) {
		    throw new DBAccessException(e);
		}catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
		}

	}

}
