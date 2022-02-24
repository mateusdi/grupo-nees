package br.com.nees.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.nees.model.Atividade;
import br.com.nees.model.Frequencia;
import br.com.nees.model.Grupo;
import br.com.nees.model.TipoAtividade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtividadeNovoDto {
	private int id;
	@NotEmpty
	@NotBlank
	private String titulo;
	
	private String descricao;
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date prazoEntrega;
	
	private Timestamp dataCriacao;
	
	@NotNull
	private TipoAtividade tipo; 
	private boolean visivel;// visivel;


	private Frequencia frequencia;

	
	private Grupo grupo;

	// atribulo auxilar não mapeado pelo hibernate
//	@Transient
//	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
//	private Date dataAuxiliar;

	public Atividade toAtividade() {

		Atividade atividade = new Atividade();
		atividade.setId(this.id);
		atividade.setTipo(this.tipo);
		atividade.setTitulo(this.titulo);
		atividade.setDescricao(this.descricao);
		atividade.setPrazoEntrega(this.prazoEntrega);
		atividade.setDataCriacao(this.dataCriacao);
		atividade.setVisivel(this.visivel);
		atividade.setGrupo(this.grupo);

		return atividade;
	}

	public Atividade toAtividade(Atividade atividade) {
		atividade.setId(this.id);
		atividade.setTipo(this.tipo);
		atividade.setTitulo(this.titulo);
		atividade.setDescricao(this.descricao);
		atividade.setPrazoEntrega(this.prazoEntrega);
		atividade.setDataCriacao(this.dataCriacao);
		atividade.setVisivel(this.visivel);
		atividade.setGrupo(this.grupo);


		return atividade;
	}

	public void fromAtividade(Atividade atividade) {
		this.id = atividade.getId();
		this.tipo = atividade.getTipo();
		this.titulo = atividade.getTitulo();
		this.descricao = atividade.getDescricao();
		this.prazoEntrega = atividade.getPrazoEntrega();
		this.dataCriacao = atividade.getDataCriacao();
		this.visivel = atividade.isVisivel();
		this.grupo = atividade.getGrupo();

	}

}
