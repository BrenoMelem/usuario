package com.javanauta.usuario.business.dtos;

import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String nome;
    private String email;
    private String senha;
    private List<EnderecoDto> enderecos;
    private List<TelefoneDto> telefones;
}
