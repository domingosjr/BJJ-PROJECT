package com.jiujitsu.crud.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 100)
	@Getter
	@Setter
	private String nome;

	@NotBlank(message = "Faixa é obrigatória")
	@Size(max = 30)
	@Getter
	@Setter
	private String faixa;

	@NotBlank(message = "Telefone é obrigatório")
	@Size(max = 20)
	@Getter
	@Setter
	private String telefone;

	@Email(message = "E-mail inválido")
	@NotBlank(message = "E-mail é obrigatório")
	@Getter
	@Setter
	private String email;

	@NotBlank(message = "Plano é obrigatório")
	@Size(max = 50)
	@Getter
	@Setter
	private String plano;

	@Override
	public int hashCode() {
		return Objects.hash(email, faixa, id, nome, plano, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(email, other.email) && Objects.equals(faixa, other.faixa) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(plano, other.plano)
				&& Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + ", faixa=" + faixa + ", telefone=" + telefone + ", email=" + email
				+ ", plano=" + plano + "]";
	}

}
