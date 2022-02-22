package br.com.nees.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.nees.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="membro")
@Getter
@Setter
public class Membro{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "nascimento")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date nascimento;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "rg")
	private String rg;
	
	@ManyToOne//errado esse mapeamento
	@JoinColumn(name="orgao_expedidor_id", referencedColumnName = "id")
	private OrgaoExpedidor orgaoExpedidor;
	
	@Column(name = "ativo")
	private Status status;
	
	@Column(name = "admin")
	private boolean admin;
	
	@Column(name = "senha")
	private String senha; //fazer a logistica pra gerar a senha e o hash
	
	@Column(name = "email")
	private String email;
	
	@ManyToOne//errado esse mapeamento
	@JoinColumn(name="sexo_id", referencedColumnName = "id")
	private Sexo sexo; //ISO/IEC 5218
	
	@OneToOne(mappedBy = "membro", cascade=CascadeType.ALL, orphanRemoval = true) 
	private Endereco endereco;
	
	@OneToMany(mappedBy = "membro", cascade= CascadeType.ALL)
	private List<Telefone> telefones  = new ArrayList<>();
	

	@OneToMany(mappedBy = "membro")
	private List<MembroGrupo> membroGrupos = new ArrayList<>();
	
	public Membro() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return nome;
	}


	
}
