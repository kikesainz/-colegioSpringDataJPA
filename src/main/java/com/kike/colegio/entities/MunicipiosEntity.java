package com.kike.colegio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="municipios")
public class MunicipiosEntity {
	
	@Id
	@Column(name="id_municipio")
	private int idMunicipio;
	
	@Column(name = "id_provincia")
	private int idProvincia;
	
	@Column(name = "nombre")
	private String descripcion;
	
	
	

	public MunicipiosEntity() {
		super();
	}




	public MunicipiosEntity(int idMunicipio, int idProvincia, String descripcion) {
		super();
		this.idMunicipio = idMunicipio;
		this.idProvincia = idProvincia;
		this.descripcion = descripcion;
	}




	public int getIdMunicipio() {
		return idMunicipio;
	}




	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}




	public int getIdProvincia() {
		return idProvincia;
	}




	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}




	public String getDescripcion() {
		return descripcion;
	}




	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}




}