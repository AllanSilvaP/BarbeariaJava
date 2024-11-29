package model;

public class Produto {
    private int id;
    private String nome;
    private int quantidade;
    private double precoCompra;
    private double precoVenda;
    private String dataEntrada;

    // Construtor e getters/setters para fazer funcionar isso dai
    
    public Produto(int id, String nome, int quantidade, double precoCompra, double precoVenda, String dataEntrada) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.dataEntrada = dataEntrada;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoCompra() { return precoCompra; }
    public double getPrecoVenda() { return precoVenda; }
    public String getDataEntrada() { return dataEntrada; }
}
