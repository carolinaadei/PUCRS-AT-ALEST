import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static class No {
        String chave;
        No esquerda, direita;

        public No(String chave, No esquerda, No direita) {
            this.chave = chave;
            this.esquerda = esquerda;
            this.direita = direita;
        }
    }

    private No raiz;
    private int tamanho;

    public void inserir(String chave) {
        raiz = inserir(raiz, chave);
    }

    private No inserir(No atual, String chave) {
        if (atual == null) {
            tamanho++;
            return new No(chave, null, null);
        }
        if (chave.compareTo(atual.chave) < 0) {
            atual.esquerda = inserir(atual.esquerda, chave);
        } else if (chave.compareTo(atual.chave) > 0) {
            atual.direita = inserir(atual.direita, chave);
        }
        return atual;
    }

    public void emOrdem() {
        emOrdemRec(raiz);
    }

    private void emOrdemRec(No atual) {
        if (atual != null) {
            emOrdemRec(atual.esquerda);
            System.out.println(atual.chave);
            emOrdemRec(atual.direita);
        }
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(No atual) {
        if (atual == null) {
            return -1;
        }
        return 1 + Math.max(alturaRec(atual.esquerda), alturaRec(atual.direita));
    }

    public int tamanho() {
        return tamanho;
    }

    public int nivelArvore(String chave) {
        return nivelArvore(raiz, chave, 0);
    }

    private int nivelArvore(No atual, String chave, int nivel) {
        if (atual == null) {
            return -1;
        }
        if (chave.equals(atual.chave)) {
            return nivel;
        } else if (chave.compareTo(atual.chave) < 0) {
            return nivelArvore(atual.esquerda, chave, nivel + 1);
        } else {
            return nivelArvore(atual.direita, chave, nivel + 1);
        }
    }

    public void imprimirArvore() {
        imprimirArvoreRec(raiz, 0);
    }

    private void imprimirArvoreRec(No atual, int nivel) {
        if (atual != null) {
            imprimirArvoreRec(atual.direita, nivel + 1);
            for (int i = 0; i < nivel; i++) {
                System.out.print("    ");
            }
            System.out.println(atual.chave);
            imprimirArvoreRec(atual.esquerda, nivel + 1);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        try (BufferedReader br = new BufferedReader(new FileReader("frutas.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                main.inserir(linha.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            return;
        }

        main.imprimirArvore();
        System.out.print("\n");

        System.out.println("Informações da árvore:");
        System.out.println("Tamanho da árvore: " + main.tamanho());
        System.out.println("Altura da árvore: " + main.altura());

        String chaveBusca = "abacate";
        int nivel = main.nivelArvore(chaveBusca);
            if (nivel != -1) {
            System.out.println("\nA fruta " + chaveBusca + " está no nível " + nivel);
            } else {
            System.out.println("\nA fruta " + chaveBusca + " não foi encontrada");
            }

        System.out.println(" \nCaminhamento em ordem central:");
        main.emOrdem();
    }
}