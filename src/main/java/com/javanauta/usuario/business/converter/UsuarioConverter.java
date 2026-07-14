package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dtos.EnderecoDto;
import com.javanauta.usuario.business.dtos.TelefoneDto;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.entity.Enderecos;
import com.javanauta.usuario.infraestructure.entity.Telefone;
import com.javanauta.usuario.infraestructure.entity.Usuario;
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
                .enderecos(usuarioDto.getEnderecos() != null ? paraListaEndereco(usuarioDto.getEnderecos()) : null)
                .telefones(usuarioDto.getTelefones() != null ? paraListaTelefone(usuarioDto.getTelefones()): null)
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
                .enderecos(usuarioDto.getEnderecos() !=null ? paraListaEnderecoDto(usuarioDto.getEnderecos()): null)
                .telefones(usuarioDto.getTelefones() != null ? paraListaTelefoneDto(usuarioDto.getTelefones()) : null)
                .build();

    }
    public List<EnderecoDto> paraListaEnderecoDto(List<Enderecos> enderecoDtos){
        return enderecoDtos.stream().map(this::paraEnderecoDto).toList();
    }
    public EnderecoDto paraEnderecoDto(Enderecos endereco){
        return EnderecoDto.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .complemento(endereco.getComplemento())
                .numero(endereco.getNumero())
                .build();
    }
    public List<TelefoneDto> paraListaTelefoneDto(List<Telefone> telefoneDtos){
        return telefoneDtos.stream().map(this::paraTelefoneDto).toList();
    }
    public  TelefoneDto paraTelefoneDto(Telefone telefone){
        return TelefoneDto.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    } // COMPARAÇÕES DA ENTITY E DA DTO
    //Utilizar o Operador ternario dentro do build
    public Usuario updateUsuario (UsuarioDto usuarioDto, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                //usuario passou uma senha nova, senão pega uma senha que tá na entity
                .nome(usuarioDto.getNome() != null? usuarioDto.getNome() : entity.getNome())
                .email(usuarioDto.getEmail() !=null ? usuarioDto.getEmail() : entity.getEmail())
                .senha(usuarioDto.getSenha() !=null ? usuarioDto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }
    // CRIANDO CONVERTER PARA O ENDERECO ENTITY
    public Enderecos updateEndereco (EnderecoDto dto, Enderecos entity){
        return Enderecos.builder()
                .id(entity.getId())
                .rua(dto.getRua() !=null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() !=null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado()!= null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .usuario_id(entity.getUsuario_id())
                .build();
    }
    public  Telefone updateTelefone (TelefoneDto dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .usuario_id(entity.getUsuario_id())
                .build();
    }
    public Enderecos paraEnderecoEntity (EnderecoDto dto, Long idUsuario){
        return Enderecos.builder()
                .rua(dto.getRua())
                .numero(dto.getNumero())
                .complemento(dto.getComplemento())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .cep(dto.getCep())
                .usuario_id(idUsuario)
                .build();
    }
    public Telefone paraTelefoneEntity (TelefoneDto dto, Long idUsuario){
        return Telefone.builder()
                .numero(dto.getNumero())
                .ddd(dto.getDdd())
                .usuario_id(idUsuario)
                .build();
    }
}

