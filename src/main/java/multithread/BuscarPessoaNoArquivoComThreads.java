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

import static oneThread.BuscarPessoaNoArquivoComUmaThread.nomeDaPessoa;

public class BuscarPessoaNoArquivoComThreads {

    private static final int NUM_THREADS = 2;
    private static volatile boolean achouNome = false; // Variável de controle para indicar se o nome foi encontrado

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(nomeDaPessoa);
        String nomeBusca = scanner.nextLine();
        File diretorio = new File("C:\\Users\\guilh\\Documents\\Faculdade\\comp paralela e distruida\\thread\\src\\dataset/");
        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt"));
        Instant inicio = Instant.now();

        int numArquivosPorThread = (int) Math.ceil((double) arquivos.length / NUM_THREADS);

        List<Thread> threads = new ArrayList<>();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < numArquivosPorThread; i++) {
                if (!achouNome) {
                    try {
                        buscaNomeCimaBaixo(arquivos[i], nomeBusca);
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

        Thread thread2 = new Thread(() -> {
            for (int i = arquivos.length - 1; i >= arquivos.length - numArquivosPorThread; i--) {
                if (!achouNome) {
                    try {
                        buscaNomeBaixoCima(arquivos[i], nomeBusca);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread2.start();
        threads.add(thread2);

        for (Thread thread : threads) {
            thread.join();
        }

        Instant fim = Instant.now();
        Duration duracao = Duration.between(inicio, fim);
        System.out.println("Tempo total de execução: " + duracao.getSeconds() + "s");
    }

    public static void buscaNomeCimaBaixo(File arquivo, String nomeBusca) throws IOException, InterruptedException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        while (linha != null & !achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Thread 1");
                System.out.println("Arquivo: " + arquivo.getName() + ", linha: " + numLinha + ", texto: " + linha);
                achouNome = true;
            }
            linha = leitor.readLine();
            numLinha++;
        //    Thread.sleep(1);
        }
        leitor.close();
    }

    public static void buscaNomeBaixoCima(File arquivo, String nomeBusca) throws IOException, InterruptedException {
        BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
        String linha = leitor.readLine();
        int numLinha = 1;
        while (linha != null && !achouNome) {
            if (linha.contains(nomeBusca)) {
                System.out.println("Thread 2");
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