package com.estudo.loja.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(

        @NotBlank(message = "Rua é obrigatória")
        String rua,

        @NotBlank(message = "Número é obrigatório")
        String numero,

        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @NotBlank(message = "Cidade é obrigatória")
        String cidade,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve possuir 2 caracteres")
        String estado,

        @NotBlank(message = "CEP é obrigatório")
        String cep
) {
}
