package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Empleado;
import com.bolsadeideas.springboot.app.models.entity.Registro;
import com.bolsadeideas.springboot.app.models.entity.ItemRegistro;
import com.bolsadeideas.springboot.app.models.entity.Implemento;
import com.bolsadeideas.springboot.app.models.service.IEmpleadoService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/registro")
@SessionAttributes("registro")
public class RegistroController {

	@Autowired
	private IEmpleadoService empleadoService;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Registro registro = empleadoService.fetchRegistroByIdWithEmpleadoWhithItemRegistroWithImplemento(id); // empleadoService.findRegistroById(id);

		if (registro == null) {
			flash.addFlashAttribute("error", "La registro no existe en la base de datos!");
			return "redirect:/listar";
		}

		model.addAttribute("registro", registro);
		model.addAttribute("titulo", "Registro: ".concat(registro.getDescripcion()));
		return "registro/ver";
	}

	@GetMapping("/form/{empleadoId}")
	public String crear(@PathVariable(value = "empleadoId") Long empleadoId, Map<String, Object> model,
			RedirectAttributes flash) {

		Empleado empleado = empleadoService.findOne(empleadoId);

		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no existe en la base de datos");
			return "redirect:/listar";
		}

		Registro registro = new Registro();
		registro.setEmpleado(empleado);

		model.put("registro", registro);
		model.put("titulo", "Crear Registro");

		return "registro/form";
	}

	@GetMapping(value = "/cargar-implementos/{term}", produces = { "application/json" })
	public @ResponseBody List<Implemento> cargarImplementos(@PathVariable String term) {
		return empleadoService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Registro registro, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Registro");
			return "registro/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Registro");
			model.addAttribute("error", "Error: La registro NO puede no tener líneas!");
			return "registro/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Implemento implemento = empleadoService.findImplementoById(itemId[i]);

			ItemRegistro linea = new ItemRegistro();
			linea.setCantidad(cantidad[i]);
			linea.setImplemento(implemento);
			registro.addItemRegistro(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		empleadoService.saveRegistro(registro);
		status.setComplete();

		flash.addFlashAttribute("success", "Registro creada con éxito!");

		return "redirect:/ver/" + registro.getEmpleado().getId();
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		Registro registro = empleadoService.findRegistroById(id);

		if (registro != null) {
			empleadoService.deleteRegistro(id);
			flash.addFlashAttribute("success", "Registro eliminada con éxito!");
			return "redirect:/ver/" + registro.getEmpleado().getId();
		}
		flash.addFlashAttribute("error", "La registro no existe en la base de datos, no se pudo eliminar!");

		return "redirect:/listar";
	}

}
