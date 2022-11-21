package com.modusoftware.pruebaTecnica2.web.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.modusoftware.pruebaTecnica2.business.ILibroService;
import com.modusoftware.pruebaTecnica2.business.dto.LibroDTO;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTO;
import com.modusoftware.pruebaTecnica2.persistent.model.LibroEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/book")
@Log4j2
public class LibroController {
	
	
	@Autowired
	private ILibroService libroService;
	
	@Operation(summary = "Obtener todos los libros")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "OK", 
					     content = @Content(schema = @Schema(implementation = LibroDTO.class), 
					     mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Error del servidor"), 
			})
	 @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
		
	public ResponseEntity<List<LibroDTO>> getAll() {
		 try{
			List<LibroDTO> getAllBooks = this.libroService.getAllOrderByNameAsc();

			return new ResponseEntity<List<LibroDTO>>(getAllBooks, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Error en getAll", e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	 
	 
	
	@Operation(summary = "Buscar libro por nombre_libro")
	 @ApiResponses({
	      @ApiResponse(responseCode = "200", description = "OK",
	    		       content = @Content(schema = @Schema(implementation = LibroDTO.class), 
				       mediaType = MediaType.APPLICATION_JSON_VALUE)),
	      @ApiResponse(responseCode = "204", description = "No existe un libro con nombre ingresado"),
	      @ApiResponse(responseCode = "500", description = "Error del servidor"),
	    })
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
			    path = "/nombre/{nombre_libro}")
	public ResponseEntity<List<LibroDTO>> findByBookName(@Parameter(name = "nombre_libro", description = "Inserte el Nombre del libro a buscar", 
	                                                                example = "El Camino", 
	                                                                required = true)
	                                                     @PathVariable("nombre_libro") String nameBook){
	     try {                                        
			List<LibroDTO> findByName = this.libroService.findByNameOrderByNameAsc(nameBook);

			if (findByName.isEmpty()) {
				log.error("No existe un libro con nombre " + nameBook);
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<LibroDTO>>(findByName, HttpStatus.OK);
		
		}catch(Exception e) {
			log.error("Error en findByBookName", e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	
	
	@Operation(summary = "Crear libro",
			   requestBody = @io.swagger.v3.oas.annotations.parameters
			 .RequestBody(description = "Agregue en el PayLoad la informacion del libro a crear ",
					 content = @Content(schema = @Schema(implementation = LibroDTO.class), 
					 mediaType = MediaType.APPLICATION_JSON_VALUE), 
					 required = true))
	 @ApiResponses({
	            @ApiResponse(responseCode = "200", description = "Responde con el ID del libro creado",
	            		     content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, 
	    				     examples = {@ExampleObject(value = "'12'")})), 
	            @ApiResponse(responseCode = "400", description = "Caracteres no validos"),
	            @ApiResponse(responseCode = "409", description = "Duplicate ISBN this property is UNIQUE or All Ready ID exist"),
	            @ApiResponse(responseCode = "500", description = "Error del servidor"),
	    })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Integer> createBook(@RequestBody LibroDTO dto) {
		   try{

			   LibroDTO dtoId = this.libroService.saveBook(dto);
			
			if (dtoId.getId() == null || dtoId.getId() == 0) {
				
				log.error("Duplicate ISBN this property is UNIQUE or");
				log.error("All Ready ID exist");
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<Integer>(dtoId.getId(), HttpStatus.OK);
		   }catch(IllegalArgumentException ie) {
				log.warn("Caracteres no validos", ie);

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		   }catch(Exception e) {
			log.error("Error en createBook", e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
		
	}
	
	 @Operation(summary = "Agregar Stock",
			    requestBody = @io.swagger.v3.oas.annotations.parameters
			 .RequestBody(description = "Agrege a la propiedad stock la cantidad deseada ",
					 content = @Content(schema = @Schema(implementation = LibroDTO.class), 
					 mediaType = MediaType.APPLICATION_JSON_VALUE, 
					 examples = {@ExampleObject(value = "'stock': 5")}),
					 required = true))
	 @ApiResponses({
	            @ApiResponse(responseCode = "201",description = "Actualizado",
	            		     content = @Content(schema = @Schema(implementation = LibroDTO.class), 
	            		     mediaType = MediaType.APPLICATION_JSON_VALUE)),
	            @ApiResponse(responseCode = "204", description =  "No existe un libro con ID ingresado"),
	            @ApiResponse(responseCode = "304", description = "Stock sin cambios"),
	            @ApiResponse(responseCode = "400", description = "Caracteres no validos"),
	            @ApiResponse(responseCode = "500", description = "Error del servidor"),
	    })
	@PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
                 path = "/{id_book}")
	public ResponseEntity<LibroDTO> updateStock(
			@Parameter(name = "id_book", description = "Id del libro para agregar stock", example = "3", required = true)
			@PathVariable("id_book")Integer id,
	        @RequestBody LibroDTO dto){
		try{	  
			
		   LibroDTO dtoStock = this.libroService.addStockBook(id,dto);
		   if(dtoStock.getId() == null) {
			   log.warn("Stock sin cambios");
			 return new ResponseEntity<> (dtoStock, HttpStatus.NOT_MODIFIED);
		   }
		   return new ResponseEntity<LibroDTO> (dtoStock, HttpStatus.CREATED);
		   
		  }catch(NoSuchElementException ex ) {
				log.warn("ID no encontrado",ex);
				
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				
		  }catch(IllegalArgumentException ie) {
				log.warn("Caracteres no validos", ie);

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
	     }catch(Exception e) {
			log.error("Error en updateStock", e);			
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
	
	
	}
	
	

