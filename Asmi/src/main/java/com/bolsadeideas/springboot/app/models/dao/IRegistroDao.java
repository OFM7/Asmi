package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Registro;

public interface IRegistroDao extends CrudRepository<Registro, Long>{

	@Query("select f from Registro f join fetch f.empleado c join fetch f.items l join fetch l.implemento where f.id=?1")
	public Registro fetchByIdWithEmpleadoWhithItemRegistroWithImplemento(Long id);
}
