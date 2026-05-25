package com.javanauta.usuario.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Telefone")
public class Telefone {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero", length = 20)
    private String numero;
    @Column (name = "ddd", length = 3)
    private String ddd;
    @Column (name = "usuario_id")
    private Long usuario_id;

}
