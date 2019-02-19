package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Implemento;

public interface IImplementoDao extends CrudRepository<Implemento, Long>{

	@Query("select p from Implemento p where p.nombre like %?1%")
	public List<Implemento> findByNombre(String term);
	
	public List<Implemento> findByNombreLikeIgnoreCase(String term);
}
