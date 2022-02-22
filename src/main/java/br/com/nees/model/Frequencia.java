package br.com.nees.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="frequencia")
@Getter
@Setter
public class Frequencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") 
	private Date dataFrequencia;
	
	@OneToOne
	@JoinColumn(name="atividade_id", referencedColumnName = "id")
	private Atividade atividade;
	
	@OneToMany(mappedBy = "frequencia", cascade = CascadeType.ALL , orphanRemoval = true)
	private List<FrequenciaMembro> frequenciaMembro = new ArrayList<>();
	
}
