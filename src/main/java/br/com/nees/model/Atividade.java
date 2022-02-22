package br.com.nees.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
//os usuarios inativos nao poderam acessar o sistema

@Entity
@Table(name = "atividade")
@Getter
@Setter
public class Atividade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String titulo;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date prazoEntrega;

	@Column(updatable = false)
	private Timestamp dataCriacao;

	@ManyToOne
	private TipoAtividade tipo;

	private boolean visivel;// visivel;

//	@OneToOne(mappedBy = "atividade", cascade=CascadeType.ALL, orphanRemoval = true)
//	private Frequencia frequencia;

	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

//	//atribulo auxilar não mapeado pelo hibernate
//	@Transient
//	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") 
//	private Date dataAuxiliar;
}
