package com.modusoftware.pruebaTecnica2.business.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.modusoftware.pruebaTecnica2.business.ILibroService;
import com.modusoftware.pruebaTecnica2.business.IVentaService;
import com.modusoftware.pruebaTecnica2.business.dto.LibroDTO;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTO;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTOGetAll;
import com.modusoftware.pruebaTecnica2.business.dto.VentaDTOReturn;
import com.modusoftware.pruebaTecnica2.business.mapper.Mapeo;
import com.modusoftware.pruebaTecnica2.persistent.model.LibroEntity;
import com.modusoftware.pruebaTecnica2.persistent.model.VentaEntity;
import com.modusoftware.pruebaTecnica2.persistent.repository.LibroRepository;
import com.modusoftware.pruebaTecnica2.persistent.repository.VentaRepository;

@Service
public class VentaServiceImpl implements IVentaService {
	
	
	@Autowired
	private VentaRepository  ventaRepository;
	
	@Autowired
	private LibroRepository libroRepository;
	
    @Autowired
    private ILibroService libroService;
    
    @Autowired
	private Mapeo mapeo;
    
    

	@Override
	public VentaDTOReturn registroVentas(VentaDTO dto) {
		
		
		Integer stock = this.libroService.cantStock(dto.getId());
		
		if (dto.getCantidad() > stock) {
			
		VentaDTOReturn notModify = new VentaDTOReturn();
		notModify.setLibroId(null);
		
		return notModify;
		}
		LibroEntity ent = this.libroService.updateStockBook(dto.getId(), dto.getCantidad());
		
		VentaDTO dtoV = mapeo.toVentaDTO(ent);
		dtoV.setFechaVenta(LocalDateTime.now());
		dtoV.setCantidad(dto.getCantidad());
		
		VentaEntity venta = mapeo.toVentaEntity(dtoV);

		this.ventaRepository.save(venta);
		
		VentaDTOReturn result = mapeo.toVentaDTOReturn(venta);
		result.setValor(dtoV.getValor());
		
		
		return result;
	}

	@Override
	public List<VentaDTOGetAll> getAllVentas() {
		
		List<VentaEntity> allVentas = (List<VentaEntity>) this.ventaRepository.findAllVentas();
		List<VentaDTOGetAll> all = mapeo.toVentaDTOall(allVentas);
		
		return all;
	}
	
	

    
    
    
    
    
    
    
    
    
    
    
    
    
    


    
	



	
	
}
