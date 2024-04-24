package com.cursos.springsecuritycourse.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null
    private Long id;

    @NotBlank
    private String name;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

}
