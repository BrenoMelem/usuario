package com.javanauta.usuario.business.dtos;

import jakarta.persistence.Column;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDto {
    private Long id;
    private String numero;
    private String ddd;


}
