package ar.unlam.intraconsulta;

import java.util.ArrayList;
import java.util.Iterator;

public class Universidad {

	// private Alumno [] alumnos;
	private ArrayList<Alumno> alumnos;
	private String nombre;
	private ArrayList<Materia> materias;
	private ArrayList <InscripcionMateria> inscripcionesMateria;

	public Universidad(String nombre) {
		this.nombre = nombre;
		this.alumnos = new ArrayList<Alumno>();
		this.materias = new ArrayList<>();
		this.inscripcionesMateria = new ArrayList<>();
	}

	public Boolean registrar(Alumno alumno) {

		if (buscarAlumnoPorDni(alumno.getDni()) == null)
		
			return this.alumnos.add(alumno);

		return false;
	}

	public Alumno buscarAlumnoPorDni(Integer dni) {

		for (int i = 0; i < alumnos.size(); i++) {
			if (this.alumnos.get(i).getDni().equals(dni))
				return this.alumnos.get(i);
		}
		
		return null;
	}

	public Boolean existeAlumno(Integer dni) {
		for (int i = 0; i < alumnos.size(); i++) {
			if (this.alumnos.get(i).getDni().equals(dni))
				return true;
		}
		return false;
	}

	public Boolean registraMateria(Materia materia) {
	   
		return this.materias.add(materia);
	}

	public boolean inscribirAlumnoAUnaMateria(Integer dni, Integer codigo) {
	
		Alumno alumno = this.buscarAlumnoPorDni(dni);
		Materia materia = this.buscarMateriaPorCodigo(codigo);
		
		if (alumno == null || materia == null) {
			return false;
		}
		
		
		ArrayList<Materia> correlativas = materia.getCorrelativas();
		for(int i = 0; i < correlativas.size(); i++) {
			Materia correlativa = correlativas.get(i);
			if(!estaAprobado(dni, correlativa.getCodigo()))
				return false;
		}
		
	    InscripcionMateria  inscripcionMateria = new InscripcionMateria (alumno,materia);
		return this.inscripcionesMateria.add(inscripcionMateria );
		
	
		

	}

	private Materia buscarMateriaPorCodigo(Integer codigo) {
		for (int i = 0; i < this.materias.size(); i++) {
			if (this.materias.get(i).getCodigo().equals(codigo))
				return this.materias.get(i);
		}
		return null;
	}
	
	public InscripcionMateria getInscripcion(Integer dni, Integer codigo) {
		
		Alumno alumno = this.buscarAlumnoPorDni(dni);
		Materia materia = this.buscarMateriaPorCodigo(codigo);
		
		for (int i = 0; i < this.inscripcionesMateria.size(); i++) {
			InscripcionMateria ins = this.inscripcionesMateria.get(i);
			Alumno alumnoIns = ins.getAlumno();
			Materia materiaIns = ins.getMateria();
			if (alumnoIns.equals(alumno) && materiaIns.equals(materia)){
				return ins;
			}	
		}
		return null;
		
	}
		
	
	public boolean calificar (Integer dni, Integer codigo, Nota nota) {
		
		Alumno alumno = this.buscarAlumnoPorDni(dni);
		Materia materia = this.buscarMateriaPorCodigo(codigo);
		
		
		if ( alumno == null || materia == null ) {
			return false;
		}


		InscripcionMateria inscripcionMateria = getInscripcion(dni, codigo);
		
		if (inscripcionMateria == null) {
			return false;
		}
		
		inscripcionMateria.setNota(nota); 

		return true;
	    
	}

	public Nota getNota(Integer dni, Integer codigo) {
		InscripcionMateria ins = getInscripcion(dni, codigo);
		return ins.getNota();
	}
	
	
	public boolean estaAprobado(Integer dni, Integer codigo) {
		Nota nota = getNota(dni, codigo);
		
		if (nota == null) {
			return false;
		}
		
		Integer valor = nota.getValor();
		return valor >= 4;
	}

		

}
