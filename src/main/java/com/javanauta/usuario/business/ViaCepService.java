package com.javanauta.usuario.business;

import com.javanauta.usuario.infraestructure.clients.ViaCepClient;
import com.javanauta.usuario.infraestructure.clients.dtos.ViaCepDto;
import com.javanauta.usuario.infraestructure.exceptions.IllegalArgumentExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient viaCepClient;

    public ViaCepDto buscarDadosEndereco(String cep) {
        return viaCepClient.buscaDadosEndereco(processarCep(cep));

    }

    //metodos para validar este CEP, seguindo a documentação do VIA CEP. VAMOS UTILIZAR O REGEX
    private String processarCep(String cep) {
        String cepFormatado = cep.replaceAll(" ", "").replace("-", "");
        if (!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {
            throw new IllegalArgumentExceptions("O Cep Contém caracteres inválidos " + cep);
        }
        return cepFormatado;
    }

}
