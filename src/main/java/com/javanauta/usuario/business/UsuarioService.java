package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dtos.EnderecoDto;
import com.javanauta.usuario.business.dtos.TelefoneDto;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.entity.Enderecos;
import com.javanauta.usuario.infraestructure.entity.Telefone;
import com.javanauta.usuario.infraestructure.entity.Usuario;
import com.javanauta.usuario.infraestructure.exceptions.ConflictException;
import com.javanauta.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infraestructure.repository.EnderecoRepository;
import com.javanauta.usuario.infraestructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDto buscarUsuarioPorEmail(String email) {
        try{
            return usuarioConverter.paraUsuarioDto
                    (usuarioRepository.findByEmail(email).orElseThrow(()->
                            new ResourceNotFoundException("Email não encontrado " + email)));
        } catch(ResourceNotFoundException e) {
            throw  new ResourceNotFoundException("Email não encontrado " + email);
        }
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
        //Criptografia da senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        //Aqui buscamos os dados do usuario no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não Encontrado"));
        // Mesclamos os dados que recebemos na requisição (DTO) com os dados do banco de dados(entity)
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        //Salvou os dados do usuario convertido e depois pegou o retorno e converteu para UsuarioDto.
        return usuarioConverter.paraUsuarioDto(usuarioRepository.save(usuario));
    }
    public EnderecoDto atualizaEndereco(Long idEndereco, EnderecoDto enderecoDto){
        Enderecos enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado " + idEndereco));
        Enderecos enderecos = usuarioConverter.updateEndereco(enderecoDto,enderecoEntity);
        return usuarioConverter.paraEnderecoDto(enderecoRepository.save(enderecos));
    }
    public TelefoneDto atualizaTelefone (Long idTelefone, TelefoneDto telefoneDto){
        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(()-> new ResourceNotFoundException("Id não encontrado " + idTelefone));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDto, telefoneEntity);
        return usuarioConverter.paraTelefoneDto(telefoneRepository.save(telefone));
    }
    public EnderecoDto cadastraEndereco (String token, EnderecoDto dto){
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email não Localizado " + email));
        Enderecos endereco=usuarioConverter.paraEnderecoEntity(dto,usuario.getId());
        Enderecos enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDto(enderecoEntity);
    }
    public TelefoneDto cadastraTelefone (String token, TelefoneDto dto) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email não localizado " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        Telefone telefoneEntity = telefoneRepository.save(telefone);
        return usuarioConverter.paraTelefoneDto(telefoneEntity);
    }
}
