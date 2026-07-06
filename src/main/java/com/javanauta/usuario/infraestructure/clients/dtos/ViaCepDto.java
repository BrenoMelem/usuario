package com.javanauta.usuario.infraestructure.clients.dtos;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepDto {

    public String cep;
    public String logradouro;
    public String complemento;
    public String unidade;
    public String bairro;
    public String localidade;
    public String uf;
    public String estado;
    public String regiao;
    public String ibge;
    public String gia;
    public String ddd;
    public String siafi;


}
