package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Empleado;
import com.bolsadeideas.springboot.app.models.entity.Registro;
import com.bolsadeideas.springboot.app.models.entity.Implemento;

public interface IEmpleadoService {

	public List<Empleado> findAll();
	
	public Page<Empleado> findAll(Pageable pageable);

	public void save(Empleado empleado);
	
	public Empleado findOne(Long id);
	
	public Empleado fetchByIdWithRegistros(Long id);
	
	public void delete(Long id);
	
	public List<Implemento> findByNombre(String term);
	
	public void saveRegistro(Registro registro);
	
	public Implemento findImplementoById(Long id);
	
	public Registro findRegistroById(Long id);
	
	public void deleteRegistro(Long id);
	
	public Registro fetchRegistroByIdWithEmpleadoWhithItemRegistroWithImplemento(Long id);

}
