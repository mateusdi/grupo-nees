package br.com.nees.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.nees.model.Endereco;
import br.com.nees.model.Membro;
import br.com.nees.model.OrgaoExpedidor;
import br.com.nees.model.Sexo;
import br.com.nees.model.Telefone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembroPerfilDto {
	
	@NotEmpty
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past
	private Date nascimento;
	
	//@NotEmpty
	//@NotNull
	private String cpf;
	
	//@NotEmpty
	//@NotNull
	private String rg;
	
	
	//@NotNull
	private OrgaoExpedidor orgaoExpedidor;
	
	//private String senha;
	
	@NotEmpty
	@NotNull
	@Email
	@NotBlank
	private String email;
	
	private Sexo sexo;
	
	private Endereco endereco;
	
	private List<Telefone> telefones  = new ArrayList<>();	
	
	public Membro toMembro() {
		
		Membro membro = new Membro();
		membro.setNome(this.nome);
		membro.setNascimento(this.nascimento);
		membro.setCpf(this.cpf);
		membro.setRg(this.rg);
		membro.setOrgaoExpedidor(this.orgaoExpedidor);
		//membro.setSenha(this.senha);
		membro.setEmail(this.email);
		membro.setSexo(this.sexo);
		membro.setEndereco(this.endereco);
		membro.setTelefones(this.telefones);

		return membro;
	}
	
	public Membro toMembro(Membro membro) {

		membro.setNome(this.nome);
		membro.setNascimento(this.nascimento);
		membro.setCpf(this.cpf);
		membro.setRg(this.rg);
		membro.setOrgaoExpedidor(this.orgaoExpedidor);
		//membro.setSenha(this.senha);
		membro.setEmail(this.email);
		membro.setSexo(this.sexo);
		membro.setEndereco(this.endereco);
		membro.setTelefones(this.telefones);

		return membro;
	}
	
	public void fromMembro(Membro membro) {

		this.nome = membro.getNome();
		this.nascimento = membro.getNascimento();
		this.cpf = membro.getCpf();
		this.rg = membro.getRg();
		this.orgaoExpedidor = membro.getOrgaoExpedidor();
		//this.senha = membro.getSenha();
		this.email = membro.getEmail();
		this.sexo = membro.getSexo();
		this.endereco = membro.getEndereco();
		this.telefones = membro.getTelefones();
	}

	
	

}
