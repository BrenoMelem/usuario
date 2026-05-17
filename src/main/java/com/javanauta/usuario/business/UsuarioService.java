package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.entity.Usuario;
import com.javanauta.usuario.infraestructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    public UsuarioDto salvaUsuario (UsuarioDto usuarioDto){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto); // CONVERTENDO DTO PARA ENTITY.
        usuario = usuarioRepository.save(usuario); // SALVANDO A ENTITY NO BANCO DE DADOS
        return usuarioConverter.paraUsuarioDto(usuario); // CONVERTENDO A ENTITY (SALVA) EM UMA DTO.
    }

}
