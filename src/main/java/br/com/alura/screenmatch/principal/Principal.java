package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d3f5c35f";
    public Principal() {
    }

    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

			for (int i = 1; i<=dados.totalTemporadas(); i++){
				json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") +
						"&season=" + i + API_KEY);
				DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
				temporadas.add(dadosTemporada);
			}
			temporadas.forEach(System.out::println);

            for (int i = 0; i < dados.totalTemporadas(); i++){
                List<DadosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
                for (int j = 0; j < episodiosTemporada.size(); j++){
                    System.out.println(episodiosTemporada.get(j).titulo());
                }
            }

            temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

//            List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo", "Nico","Iolanda");
//            nomes.stream()
//                    .sorted() // ordena por ordem alfabética
//                    .limit(3) // limita o resultado em 3
//                    .filter(n -> n.startsWith("I")) //pega todos os nomes que começam com a letra I
//                    .map (n -> n.toUpperCase()) //pega o resultado e coloca tudo como letra maiuscula
//                    .forEach(System.out::println); //imprime o resultado

        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
    }
}
