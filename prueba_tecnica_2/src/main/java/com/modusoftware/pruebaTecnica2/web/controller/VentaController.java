package com.modusoftware.pruebaTecnica2.web.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.modusoftware.pruebaTecnica2.business.ILibroService;
import com.modusoftware.pruebaTecnica2.business.IVentaService;
import com.modusoftware.pruebaTecnica2.business.dto.LibroDTO;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTO;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTOGetAll;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTOReturn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/book")
@Log4j2
public class VentaController {

	@Autowired
	private IVentaService ventaServ;
	
	
	@Operation(summary = "Compra de libros",
			 requestBody = @io.swagger.v3.oas.annotations.parameters
			 .RequestBody(description = "Agrege el ID y cantidad de libros a comprar ",
					 content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE), 
					                    required = true))
	 @ApiResponses({
	             @ApiResponse(responseCode = "200", description = "OK",
	            		  content = @Content(schema = @Schema(implementation = VentaDTOReturn.class),
	            		  mediaType = MediaType.APPLICATION_JSON_VALUE)), 
	             @ApiResponse(responseCode = "204", description = "No hay esa cantidad requerida"),
	             @ApiResponse(responseCode = "304", description = "No se vendio ningun libro"),
	             @ApiResponse(responseCode = "404", description = "ID no encontrado"),
	             @ApiResponse(responseCode = "500", description = "Error del servidor"),
	    })
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE }, 
                 produces = {MediaType.APPLICATION_JSON_VALUE }, 
                 path = "/buy")
	public ResponseEntity <VentaDTOReturn> buyBook(@RequestBody VentaDTO dto) {

		try {
			VentaDTOReturn result =	this.ventaServ.registroVentas(dto);
			
			if(result.getLibroId() == null) {
				log.warn("No hay esa cantidad requerida");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			if (dto.getCantidad() < 1) {
				log.warn("No se vendio ningun libro");
				return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			}

			return new ResponseEntity <VentaDTOReturn> (result, HttpStatus.OK);
  
		     }catch(NoSuchElementException ex) {
				log.warn("ID no encontrado", ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			 }catch(Exception e) {
			log.error("Error en ventaBook", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	@Operation(summary = "Obtener todos los ventas")
	 @ApiResponses({
		  @ApiResponse(responseCode = "200", description = "OK",
				       content = @Content(schema = @Schema(implementation = VentaDTOGetAll.class),
        		       mediaType = MediaType.APPLICATION_JSON_VALUE)), 
		  @ApiResponse(responseCode = "500", description = "Error del servidor"),
		   })
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
			    path = "/allVentas")
	public ResponseEntity<List<VentaDTOGetAll>> getAll() {
		 try{
			 
			List<VentaDTOGetAll> getAllVentas = this.ventaServ.getAllVentas();

			return new ResponseEntity<List<VentaDTOGetAll>>(getAllVentas , HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error en getAll", e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	
	
}
