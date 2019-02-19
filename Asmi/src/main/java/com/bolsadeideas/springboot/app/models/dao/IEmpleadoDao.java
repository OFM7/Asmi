package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Empleado;

public interface IEmpleadoDao extends PagingAndSortingRepository<Empleado, Long> {

	@Query("select c from Empleado c left join fetch c.registros f where c.id=?1")
	public Empleado fetchByIdWithRegistros(Long id);
}
