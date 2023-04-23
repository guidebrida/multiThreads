package speedUp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static oneThread.BuscarPessoaNoArquivoComUmaThread.*;

public class BuscarPorArquivo {

    private static volatile boolean achouNome = false;

    public static void buscaNome(String caminhoArquivo, String nomeBusca) throws IOException, InterruptedException {
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        while (linha != null && !achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Arquivo: " + caminhoArquivo + ", linha: " + numLinha + ", texto: " + linha);//quando encontrar parar a busca
                achouNome = true;
            }
            linha = leitor.readLine();
            numLinha++;
            Thread.sleep(1);
        }
        leitor.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(nomeDaPessoa);
        String nomeBusca = scanner.nextLine();
        System.out.println("Insira o caminho do arquivo:");
        String caminhoArquivo = scanner.nextLine();
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("O arquivo não existe.");
            return;
        }
        Instant inicio = Instant.now();
        List<Thread> threads = new ArrayList<>();

        Thread thread1 = new Thread(() -> {
                    try {
                        buscaNome(caminhoArquivo, nomeBusca);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


        });

        thread1.start();
        threads.add(thread1);
        for (Thread thread : threads) {
            thread.join();
        }

        buscaNome(caminhoArquivo, nomeBusca);
        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        if (!achouNome) {
            System.out.println(arquivoNaoEncontrado);
        }
        System.out.println(tempoTotal + duracao.getSeconds() + "s");
    }
}

