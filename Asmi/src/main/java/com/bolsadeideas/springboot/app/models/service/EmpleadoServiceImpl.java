package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IEmpleadoDao;
import com.bolsadeideas.springboot.app.models.dao.IRegistroDao;
import com.bolsadeideas.springboot.app.models.dao.IImplementoDao;
import com.bolsadeideas.springboot.app.models.entity.Empleado;
import com.bolsadeideas.springboot.app.models.entity.Registro;
import com.bolsadeideas.springboot.app.models.entity.Implemento;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService{

	@Autowired
	private IEmpleadoDao empleadoDao;
	
	@Autowired
	private IImplementoDao implementoDao;
	
	@Autowired
	private IRegistroDao registroDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		// TODO Auto-generated method stub
		return (List<Empleado>) empleadoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoDao.save(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Empleado findOne(Long id) {
		return empleadoDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Empleado fetchByIdWithRegistros(Long id) {
		return empleadoDao.fetchByIdWithRegistros(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empleadoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Implemento> findByNombre(String term) {
		return implementoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveRegistro(Registro registro) {
		registroDao.save(registro);
	}

	@Override
	@Transactional(readOnly=true)
	public Implemento findImplementoById(Long id) {
		return implementoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Registro findRegistroById(Long id) {
		return registroDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteRegistro(Long id) {
		registroDao.deleteById(id); // registroDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Registro fetchRegistroByIdWithEmpleadoWhithItemRegistroWithImplemento(Long id) {
		return registroDao.fetchByIdWithEmpleadoWhithItemRegistroWithImplemento(id);
	}
	
	
}
