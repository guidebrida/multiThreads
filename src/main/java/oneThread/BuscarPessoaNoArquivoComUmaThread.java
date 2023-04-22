package oneThread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static multithread.BuscarPessoaNoArquivoComThreads.buscaNomeCimaBaixo;

public class BuscarPessoaNoArquivoComUmaThread {

    public static final String nomeDaPessoa = "Digite o nome da pessoa que você procura:";
    public static final String tempoTotal = "Tempo total de execução: ";

    private static volatile boolean achouNome = false; // Variável de controle para indicar se o nome foi encontrado


    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(nomeDaPessoa);
        String nomeBusca = scanner.nextLine();
        File diretorio = new File("C:\\Users\\guilh\\Documents\\Faculdade\\comp paralela e distruida\\thread\\src\\dataset/");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        Instant inicio = Instant.now();

        List<Thread> threads = new ArrayList<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i <= arquivos.length; i++) {
                if (!achouNome){
                    try {
                        buscaNome(arquivos[i], nomeBusca);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return;

            }
        });

        thread1.start();
        threads.add(thread1);
        for (Thread thread : threads) {
            thread.join();
        }

        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        System.out.println(tempoTotal + duracao.getSeconds() + "s");
    }

    public static void buscaNome(File arquivo, String nomeBusca) throws IOException, InterruptedException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        while (linha != null  && !achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Arquivo: " + arquivo.getName() + ", linha: " + numLinha + ", texto: " + linha);
                achouNome = true;
            }
            linha = leitor.readLine();
            numLinha++;
            Thread.sleep(1);
        }
        leitor.close();
    }
}