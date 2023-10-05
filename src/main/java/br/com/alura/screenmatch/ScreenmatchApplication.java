package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner  {
	// a interface CommandLineRunner permite a criação de chamadas dentro do método principal, e tudo passa a ser feito
	// dentro do método run, que é implementado após acrecentar a interface CommandLineRunner

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			var consumoAPI = new ConsumoAPI();
			Scanner leitura = new Scanner(System.in);

			System.out.println("Digite o nome da série: ");
			var nomeSerie = leitura.nextLine().replace(" ", "+");
			String enderecoSerie = "https://www.omdbapi.com/?t=" + nomeSerie + "&apikey=d3f5c35f";
			var json = consumoAPI.obterDados(enderecoSerie);
			//System.out.println(json);
			//json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
			System.out.println(json);
			ConverteDados conversor = new ConverteDados();
			DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
			System.out.println(dados);
			System.out.println("Digite a temporada da série: ");
			var temporadaSerie = leitura.nextLine();
			System.out.println("Digite o episódio da temporada: ");
			var episodio = leitura.nextLine();
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=" + nomeSerie +
					"&season=" + temporadaSerie + "&episode="+ episodio + "&apikey=6585022c");
			DadosEpisodios dadosEpisodios = conversor.obterDados(json, DadosEpisodios.class);
			System.out.println(dadosEpisodios);
		} catch (Exception e){
			System.out.println("Nada ainda.");
		}
	}
}
