package normal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

public class BuscarPessoaNoArquivo {

    public static void buscaNome(File arquivo, String nomeBusca) throws IOException, InterruptedException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        Boolean achouNome = false;
        while (linha != null &!achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Arquivo: " + arquivo.getName() + ", linha: " + numLinha + ", texto: " + linha);//quando encontrar parar a busca
                 achouNome = true;
            }
            linha = leitor.readLine();
            numLinha++;
            Thread.sleep(1); // adiciona um timeout de 1 milissegundo
        }
        leitor.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da pessoa que voce procura?");
        String nomeBusca = scanner.nextLine();
        File diretorio = new File("C:\\Users\\guilh\\Documents\\Faculdade\\comp paralela e distruida\\thread\\src\\dataset/");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        Arrays.sort(arquivos);
        Instant inicio = Instant.now();
        for (File arquivo : arquivos) {
            buscaNome(arquivo, nomeBusca);
        }
        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        System.out.println("Tempo total de execução: " + duracao.toMillis() + "ms");
    }
}


