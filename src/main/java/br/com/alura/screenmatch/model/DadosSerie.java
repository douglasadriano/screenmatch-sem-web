package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true) //vai considerar no json apenas os atributos que foram setados aqui

public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonProperty("imdbVotes") String votos) {
}
//@JsonAlias() é utilizado dentro do record para setar o nome original do campo do json (Title, totalSeasons, imdbRating) com
// o nome dos dados de interesse do Record(titulo, totalTemporadas e avaliacao), porém, na hora que for obter os dados
// será considerado o nome do atributo do record

//@JsonProperty seta o nome do campo original (imdbVotes), com o nome do atributo do record (votos), porém, quando obter os
// dados será considerado o nome do campo original(imdbVotes)