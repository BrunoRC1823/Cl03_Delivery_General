package com.example.entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sede {
	private int idSede;
	private String nombre;
	private String direccion;
	private LocalDate fechaCreacion;
	private LocalDateTime fechaRegistro;
	private String codigoPostal;
	private int idPais;
	private Pais pais;

}
