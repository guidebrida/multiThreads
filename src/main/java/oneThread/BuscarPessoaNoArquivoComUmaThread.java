package oneThread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
public class BuscarPessoaNoArquivoComUmaThread {

    public static final String nomeDaPessoa = "Digite o nome da pessoa que você procura:";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(nomeDaPessoa);
        String nomeBusca = scanner.nextLine();
        File diretorio = new File("C:\\Users\\guilh\\Documents\\Faculdade\\comp paralela e distruida\\thread\\src\\dataset/");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        Instant inicio = Instant.now();

        for (File arquivo : arquivos) {
            buscaNome(arquivo, nomeBusca);
        }

        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        System.out.println("Tempo total de execução: " + duracao.toMillis() + "ms");
    }

    public static void buscaNome(File arquivo, String nomeBusca) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        Boolean achouNome = false;
        while (linha != null  &!achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Arquivo: " + arquivo.getName() + ", linha: " + numLinha + ", texto: " + linha);
                achouNome = true;
            }
            linha = leitor.readLine();
            numLinha++;
        }
        leitor.close();
    }
}