package br.com.nees.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="endereco")
@Getter
@Setter
public class Endereco {
	@Id
	private int id;
	
	@Column(name = "logradouro")
	private String logradouro; //campo endereço
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "bairro")
	private String bairro;//posso normalizar isso em uma nova tabela
	
	@Column(name = "complemento")
	private String complemento;

    @OneToOne
    @JoinColumn(name="membro_id", referencedColumnName = "id")
	private Membro membro;	
    
    @ManyToOne
    @JoinColumn(name="cidade_id", referencedColumnName = "id")
    private Cidade cidade;

    public Endereco() {
		// TODO Auto-generated constructor stub
	}


    

}
