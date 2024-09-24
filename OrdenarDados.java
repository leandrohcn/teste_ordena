import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrdenarDados {

    public static void main(String[] args) {
        String arquivo = "dados.txt"; // Nome do arquivo
        List<Dado> dados = new ArrayList<>();

        // Ler o arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.trim().split("\\s+");
                if (partes.length == 2) {
                    String nome = partes[0];
                    // Substituir vírgula por ponto
                    String valorString = partes[1].replace(',', '.');
                    double valor = Double.parseDouble(valorString);
                    dados.add(new Dado(nome, valor));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valor: " + e.getMessage());
        }

        // Ordenar os dados
        dados.sort(Comparator.comparingDouble(Dado::getValor));

        // Escrever os dados de volta no arquivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < dados.size(); i++) {
                Dado dado = dados.get(i);
                // Substituir ponto por vírgula ao escrever no arquivo
                String valorString = String.format("%.2f", dado.getValor()).replace('.', ',');
                bw.write(dado.getNome() + " " + valorString);
                bw.newLine();

                // Adicionar um separador a cada 10 entradas
                if ((i + 1) % 10 == 0 && (i + 1) < dados.size()) {
                    bw.write("---------------"); // Separador
                    bw.newLine(); // Linha em branco
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir os dados ordenados
        for (Dado dado : dados) {
            System.out.printf("%s\t%.2f%n", dado.getNome(), dado.getValor());
        }
    }

    static class Dado {
        private String nome;
        private double valor;

        public Dado(String nome, double valor) {
            this.nome = nome;
            this.valor = valor;
        }

        public String getNome() {
            return nome;
        }

        public double getValor() {
            return valor;
        }
    }
}
