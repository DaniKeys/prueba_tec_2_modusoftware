package com.modusoftware.pruebaTecnica2.business.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.modusoftware.pruebaTecnica2.business.ILibroService;
import com.modusoftware.pruebaTecnica2.business.dto.LibroDTO;
import com.modusoftware.pruebaTecnica2.business.mapper.Mapeo;
import com.modusoftware.pruebaTecnica2.web.controller.LibroController;
import com.modusoftware.pruebaTecnica2.persistent.model.LibroEntity;
import com.modusoftware.pruebaTecnica2.persistent.repository.LibroRepository;

import net.bytebuddy.asm.Advice.Return;

@Service
public class LibroServiceImpl implements ILibroService {

	@Autowired
	private LibroRepository libroRepository;
	
	@Autowired
	private Mapeo mapeo;

	@Override
	public List<LibroDTO> getAllOrderByNameAsc() {
		
	  List<LibroEntity> allBooks= this.libroRepository.findAll();
	  List<LibroDTO> allBooksDTO = mapeo.toLibroDTOList(allBooks);
	  
	return allBooksDTO;
		

		
	}

	@Override
	public List<LibroDTO> findByNameOrderByNameAsc(String name) {
		
        List <LibroEntity> findByNameEnt = this.libroRepository.findByNombreOrderByNombreAsc(name);
        List<LibroDTO> findByNameDTO = mapeo.toLibroDTOList(findByNameEnt);
        
        
        return findByNameDTO; 
	}

	
	@Override
	public LibroDTO saveBook(LibroDTO dto) {
		
		if(dto.getId() != null) {
			LibroDTO  exist = new LibroDTO();
			exist.setId(0);
			return exist;
		}
		
		List<LibroEntity> isbn = this.libroRepository.findByISBN(dto.getISBN());
		
		if(!isbn.isEmpty()) {
			LibroDTO conflict = new LibroDTO();
			conflict.setId(null);
			return conflict;
		}
		
			
		
		LibroEntity map = mapeo.toLibroEntity(dto);
	    this.libroRepository.save(map);
		LibroDTO dtoResult = mapeo.toLibroDTO(map);
		
	
		return dtoResult;
	}
		
	
	@Override
	@Transactional
	public LibroEntity updateStockBook(Integer id, Integer cant) {
		
	Optional<LibroEntity> entOpt = this.libroRepository.findById(id);
		
	LibroEntity ent = entOpt.get();
	Integer stock = ent.getStock() - cant;
	ent.setStock(stock);
	
	this.libroRepository.actualizarStock(ent.getId() , stock);
	
		return ent;
	}

	@Override
	@Transactional
	public LibroDTO addStockBook(Integer id, LibroDTO dto) {
		
		if(dto.getStock() < 1) {
			LibroDTO noContent = new LibroDTO();
			noContent.setId(null);
			return noContent;
		}
		
		Optional<LibroEntity> entOpt = this.libroRepository.findById(id);
		
		LibroEntity ent = entOpt.get();
		Integer stock = ent.getStock() + dto.getStock();
		ent.setStock(stock);
		
		LibroDTO dtoResult = mapeo.toLibroDTO(ent);

		return dtoResult;
	}

	@Override
	public Integer cantStock(Integer id) {
		Optional<LibroEntity> libro = this.libroRepository.findById(id);
		LibroDTO dto = mapeo.toLibroDTO(libro.get());
		
	
		return dto.getStock();
	}

	@Override
	public int countId() {
		int count =(int)this.libroRepository.count();
		return count;
	}

	
	
		
		
	}
	
	
	
	
	
	


	

	
