package br.com.nees.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import br.com.nees.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="grupo")
@Getter
@Setter
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	
	@Column(name = "data_criacao", updatable = false)
	private Timestamp dataCriacao;
	
	//@Column(name = "tipo")
	//private String tipo; //aqui pode ser um enum e uma string
	
	
	@Column(name = "ativo")
	private Status status; //creio que precise de um status. um enum de fato
	
//	@ManyToMany(mappedBy = "grupos")
//    private List<Membro> membros = new ArrayList<>();
//	

	
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.REMOVE)
	private List<MembroGrupo> membroGrupos = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "grupo", cascade= CascadeType.ALL )
	private List<Atividade> atividades = new ArrayList<>();
	
	public void fromGrupo(Grupo grupo) {
		this.id = grupo.getId();
		this.nome = grupo.getNome();
		this.descricao = grupo.getDescricao();
		this.dataCriacao = grupo.getDataCriacao();
		this.status = grupo.getStatus();
		this.membroGrupos = grupo.getMembroGrupos();
		this.atividades = grupo.getAtividades();

	}
	
}
