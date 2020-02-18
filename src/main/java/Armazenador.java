import java.util.ArrayList;

public class Armazenador {

    public static void main(String[] args) {
        long tempoInicial = System.currentTimeMillis();
       
        String nomeArquivo = args[0];
        Arquivo arquivo = new Arquivo(nomeArquivo);
        arquivo.escreverArquivo(arquivo.lerArquivo());

        
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("%.3f ms%n", (tempoFinal - tempoInicial) / 1000d);

    }

}

