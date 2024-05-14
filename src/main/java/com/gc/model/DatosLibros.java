package com.gc.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(@JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<DatosAutores> autores,
                          @JsonAlias("languages") List<String> idiomas,
                          @JsonAlias("download_count") Double numeroDescargas
) {
}
