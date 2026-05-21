package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.entity.Usuario;
import com.javanauta.usuario.infraestructure.exceptions.ConflictException;
import com.javanauta.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infraestructure.repository.UsuarioRepository;
import com.javanauta.usuario.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDto salvaUsuario(UsuarioDto usuarioDto) {
        emailExiste(usuarioDto.getEmail());
        usuarioDto.setSenha(passwordEncoder.encode(usuarioDto.getSenha())); // vai ser convertido logo depois para entity
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDto); // CONVERTENDO DTO PARA ENTITY.
        usuario = usuarioRepository.save(usuario); // SALVANDO A ENTITY NO BANCO DE DADOS
        return usuarioConverter.paraUsuarioDto(usuario); // CONVERTENDO A ENTITY (SALVA) EM UMA DTO.
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email Já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    } // TERNARIO--> PUT
     //Criptografia da senha, se caso utilizamos esse metodo, poderia criptografar a criptografia, causar gerando um erro.
     //usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    //pegar o email do usuario atraves do token. Utilizar o metodo extractUsername do security
    public UsuarioDto atualizaDadosDeUsuario(String token, UsuarioDto dto) {
        // Aqui buscamos o email do usuario através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractUsername(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        //Aqui buscamos os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não Encontrado"));
        // Mesclamos os dados que recebemos na requisição (DTO) com os dados do banco de dados(entity)
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        //Criptografia da senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        //Salvou os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDto.
        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }
}
