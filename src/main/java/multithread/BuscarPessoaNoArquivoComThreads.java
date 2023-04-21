package multithread;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BuscarPessoaNoArquivoComThreads {

    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o nome da pessoa que você procura:");
        String nomeBusca = scanner.nextLine();
        File diretorio = new File("C:\\Users\\guilh\\Documents\\Faculdade\\comp paralela e distruida\\thread\\src\\dataset/");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        Instant inicio = Instant.now();

        int numArquivosPorThread = (int) Math.ceil((double) arquivos.length / NUM_THREADS);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            int inicioThread = i * numArquivosPorThread;
            int fimThread = Math.min(inicioThread + numArquivosPorThread, arquivos.length);
            Thread thread = new Thread(() -> {
                for (int j = inicioThread; j < fimThread; j++) {
                    try {
                        buscaNome(arquivos[j], nomeBusca);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join();
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