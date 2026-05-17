package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dtos.EnderecoDto;
import com.javanauta.usuario.business.dtos.TelefoneDto;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.entity.Enderecos;
import com.javanauta.usuario.infraestructure.entity.Telefone;
import com.javanauta.usuario.infraestructure.entity.Usuario;
import org.hibernate.sql.results.spi.ListResultsConsumer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {
    //Com e Sem buIlder.
    //DENTRO DO ATIRUBUTO NOME DA NOSSA ENTITIDADE USUARIO ESTAMOS PASSANDO O USUARIODTO.GETNOME
    public Usuario paraUsuario(UsuarioDto usuarioDto){
        return Usuario.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEndereco(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDto.getTelefones()))
                .build();

    }
    public List<Enderecos> paraListaEndereco (List<EnderecoDto> enderecoDtos){
        List<Enderecos> enderecos = new ArrayList<>();
        for(EnderecoDto enderecoDto : enderecoDtos){
            enderecos.add(paraEndereco(enderecoDto));
        } return enderecos;

    }
    public  Enderecos paraEndereco (EnderecoDto enderecoDto){
        return Enderecos.builder()
                .cep(enderecoDto.getCep())
                .rua(enderecoDto.getRua())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .complemento(enderecoDto.getComplemento())
                .numero(enderecoDto.getNumero())
                .build();
    }
    public List<Telefone> paraListaTelefone (List<TelefoneDto> telefoneDtos){
        return telefoneDtos.stream().map(this::paraTelefone).toList();
    }
    public  Telefone paraTelefone (TelefoneDto telefoneDto){
        return Telefone.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .build();
    } // REALIZANDO A CONVERSÃO DE DTO PARA ENTITY (O INVERSO REALIZADO)
    public UsuarioDto paraUsuarioDto(Usuario usuarioDto){
        return UsuarioDto.builder()
                .nome(usuarioDto.getNome())
                .email(usuarioDto.getEmail())
                .senha(usuarioDto.getSenha())
                .enderecos(paraListaEnderecoDto(usuarioDto.getEnderecos()))
                .telefones(paraListaTelefoneDto(usuarioDto.getTelefones()))
                .build();

    }
    public List<EnderecoDto> paraListaEnderecoDto(List<Enderecos> enderecoDtos){
        return enderecoDtos.stream().map(this::paraEnderecoDto).toList();
    }
    public EnderecoDto paraEnderecoDto(Enderecos enderecoDto){
        return EnderecoDto.builder()
                .cep(enderecoDto.getCep())
                .rua(enderecoDto.getRua())
                .cidade(enderecoDto.getCidade())
                .estado(enderecoDto.getEstado())
                .complemento(enderecoDto.getComplemento())
                .numero(enderecoDto.getNumero())
                .build();
    }
    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefoneDtos){
        return telefoneDtos.stream().map(this::paraTelefoneDto).toList();
    }
    public  TelefoneDto paraTelefoneDto(Telefone telefoneDto){
        return TelefoneDto.builder()
                .numero(telefoneDto.getNumero())
                .ddd(telefoneDto.getDdd())
                .build();
    }

}

