package com.javanauta.usuario.controller;

import com.javanauta.usuario.business.UsuarioService;
import com.javanauta.usuario.business.ViaCepService;
import com.javanauta.usuario.business.dtos.EnderecoDto;
import com.javanauta.usuario.business.dtos.TelefoneDto;
import com.javanauta.usuario.business.dtos.UsuarioDto;
import com.javanauta.usuario.infraestructure.clients.dtos.ViaCepDto;
import com.javanauta.usuario.infraestructure.security.JwtUtil;
import com.javanauta.usuario.infraestructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usúario", description = "Cadastro de Usúarios")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ViaCepService viaCepService;

    @PostMapping
    @Operation(summary = "Cadastra Usuários", description = "Cria um novo Usuário")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<UsuarioDto> salvaUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDto));
    }

    @PostMapping("/login")
    @Operation(summary = "Cadastra Usuários", description = "Cria um novo Usuário")
    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public String login(@RequestBody UsuarioDto usuarioDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDto.getEmail(), usuarioDto.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    @Operation(summary = "Login de Usuários", description = "Login de Usuário")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<UsuarioDto> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deleta Usuário por Email", description = "Deleta Usuário")
    @ApiResponse(responseCode = "200", description = "Usuário Deletado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    @ApiResponse(responseCode = "401", description = " Usuário não encontrado")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualiza dados do Usuários", description = "Atualiza dados do Usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<UsuarioDto> atualizaDadosUsuario(@RequestBody UsuarioDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosDeUsuario(token, dto));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualiza Endereço do Usuários", description = "Atualiza endereço Usuário")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<EnderecoDto> atualizaEndereco(@RequestBody EnderecoDto dto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualiza Telefone dos Usuários", description = "Atualiza Telefone")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<TelefoneDto> atualizaTelefone(@RequestBody TelefoneDto dto, @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, dto));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Cadastro de endereços", description = "Cadastro do endereço")
    @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<EnderecoDto> cadastraEndereco(@RequestBody EnderecoDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, dto));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastro de telefones", description = "Cadastro de telefone")
    @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso")
    @ApiResponse(responseCode = "500", description = " Erro interno do servidor")
    public ResponseEntity<TelefoneDto> cadastraTelefone(@RequestBody TelefoneDto dto, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, dto));
    }
    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDto> buscaDadosEndereco (@PathVariable("cep") String cep){
        return ResponseEntity.ok(viaCepService.buscarDadosEndereco(cep));
    }

}
