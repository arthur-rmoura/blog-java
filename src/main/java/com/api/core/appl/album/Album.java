package com.api.core.appl.album;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.api.core.appl.picture.Picture;

@Entity
public class Album implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//Continuar colocando aqui outros campos
	
	@OneToMany(mappedBy="album", fetch = FetchType.LAZY)
	private List<Picture> pictureList;
	
	//Construtores
	
	//Métodos
}
