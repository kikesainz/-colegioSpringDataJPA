package com.kike.colegio.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kike.colegio.dtos.AlumnoDTO;
import com.kike.colegio.dtos.ComboDTO;
import com.kike.colegio.entities.AlumnoEntity;
import com.kike.colegio.entities.MunicipiosEntity;
import com.kike.colegio.repositorios.AlumnoRepository;
import com.kike.colegio.repositorios.MunicipioRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class AlumnosController.
 */
@Controller
public class AlumnosController {

	/** The alumno repository. */
	@Autowired
	private AlumnoRepository alumnoRepository;

	/** The municipio repository. */
	@Autowired
	private MunicipioRepository municipioRepository;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(AlumnosController.class);

	
	
	/**
	 * Formulario insertar alumnos.
	 *
	 * @param model the model
	 * @return the string
	 */
	// Entrada al menú de búsqueda (llamada GET)
	@GetMapping(value = "insertaralumno")
	public String formularioInsertarAlumnos(ModelMap model) {

		Iterable<MunicipiosEntity> listaEntidadesMunicipios = municipioRepository.findAll();
		List<ComboDTO> listaMunicipios = mapeoEntidadMunicipioComboDTO(listaEntidadesMunicipios);
		model.addAttribute("comboMunicipios", listaMunicipios);
		return "vistas/alumnos/insertarAlumnos";

	}




	/**
	 * Insertar alumno.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param idMunicipio the id municipio
	 * @param familiaNumerosa the familia numerosa
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón insertar (llamada POST)
	@PostMapping(value = "insertaralumno")
	public String InsertarAlumno(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("nombre") String nombre, @RequestParam(value = "municipios") Integer idMunicipio,
			@RequestParam(value = "familiaNumerosa") String familiaNumerosa,
			ModelMap model) {

		familiaNumerosa = (familiaNumerosa == null) ? "0" : "1";
		// Creamos la entidad Alumno que tenemos que guardar

		AlumnoEntity alumno = new AlumnoEntity(id, nombre, idMunicipio, Integer.parseInt(familiaNumerosa));

		// Guardamos la entidad Alumno
		alumnoRepository.save(alumno);

		// Recuperación del combo de municipios para devolver de nuevo el formulario
		Iterable<MunicipiosEntity> listaEntidadesMunicipios = municipioRepository.findAll();
		List<ComboDTO> listaMunicipios = mapeoEntidadMunicipioComboDTO(listaEntidadesMunicipios);
		model.addAttribute("comboMunicipios", listaMunicipios);
		return "vistas/alumnos/insertarAlumnos";
	}

	/**
	 * Formulario listado alumnos.
	 *
	 * @param model the model
	 * @return the string
	 */
	// Entrada al menú de búsqueda (llamada GET)
	@GetMapping(value = "listadoalumnos")
	public String formularioListadoAlumnos(ModelMap model) {

		return "vistas/alumnos/listadoAlumnos";

	}

	/**
	 * Listado alumnos.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón Buscar (llamada POST)
	@PostMapping(value = "listadoalumnos")
	public String listadoAlumnos(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("nombre") String nombre, ModelMap model) {
		List<AlumnoDTO> listaAlumnos = alumnoRepository.buscaAlumnoporIDyNombre(id,  nombre);
		model.addAttribute("lista", listaAlumnos);
		return "vistas/alumnos/listadoAlumnos";
	}
	
	/**
	 * Formulario actualizar alumno.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "formularioactualizaralumnos")
	public String formularioActualizarAlumno(ModelMap model) {

		return "vistas/alumnos/actualizarAlumnos";

	}

	/**
	 * Formulario buscaralumnos para actualizar.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón Buscar (llamada POST)
	@PostMapping(value = "formularioactualizaralumnos")
	public String formularioBuscaralumnosParaActualizar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("nombre") String nombre, ModelMap model) {

		List<AlumnoDTO> listaAlumnos = alumnoRepository.buscaAlumnoporIDyNombre(id,  nombre);
		model.addAttribute("lista", listaAlumnos);
		return "vistas/alumnos/actualizarAlumnos";
	}
	
	/**
	 * Actualizar alumno.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param idMunicipio the id municipio
	 * @param familiaNumerosa the familia numerosa
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón actualizar (llamada POST)
	@PostMapping(value = "actualizaralumno")
	public String actualizarAlumno(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("nombre") String nombre, @RequestParam(value = "municipios") Integer idMunicipio,
			@RequestParam(value = "familiaNumerosa", required = false) String familiaNumerosa,
			ModelMap model) {
		familiaNumerosa = (familiaNumerosa == null) ? "0" : "1";
		// Creamos la entidad Alumno que tenemos que guardar

		AlumnoEntity alumno = new AlumnoEntity(id, nombre, idMunicipio, Integer.parseInt(familiaNumerosa));
		alumnoRepository.save(alumno);
		return "vistas/alumnos/actualizarAlumnos";
	}

	/**
	 * Formulario borrado alumnos.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "formularioborraralumnos")
	public String formularioBorradoAlumnos(ModelMap model) {

		return "vistas/alumnos/borrarAlumnos";

	}

	/**
	 * Formulario buscaralumnos para borrar.
	 *
	 * @param id the id
	 * @param nombre the nombre
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón Buscar (llamada POST)
	@PostMapping(value = "formularioborraralumnos")
	public String formularioBuscaralumnosParaBorrar(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam("nombre") String nombre, ModelMap model) {

		List<AlumnoDTO> listaAlumnos = alumnoRepository.buscaAlumnoporIDyNombre(id,  nombre);
		model.addAttribute("lista", listaAlumnos);
		return "vistas/alumnos/borrarAlumnos";
	}
	
	/**
	 * Borraralumno.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	// Pulsado el botón borrar (llamada POST)
	@PostMapping(value = "borraralumno")
	public String borraralumno(@RequestParam(value = "id") Integer id, ModelMap model) {

		alumnoRepository.deleteById(id);
		return "vistas/alumnos/borrarAlumnos";
	}

	
	
	/**
	 * Mapeo entidad municipio combo DTO.
	 *
	 * @param listaEntidadesMunicipios the lista entidades municipios
	 * @return the list
	 */
	private List<ComboDTO> mapeoEntidadMunicipioComboDTO(Iterable<MunicipiosEntity> listaEntidadesMunicipios) {
		List<ComboDTO> listaMunicipios = new ArrayList<ComboDTO>(); 
		
		for (MunicipiosEntity m : listaEntidadesMunicipios ) {
			listaMunicipios.add(new ComboDTO (m.getIdMunicipio(), m.getDescripcion()));
		}
		return listaMunicipios;
	}
	

}
