package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {
    private int id;
    private LocalDateTime dataHora;
    private String cliente;
    private String servico;
    private String status;

    // Construtor
    public Agendamento(int id, LocalDateTime dataHora, String cliente, String servico, String status) {
        this.id = id;
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.servico = servico;
        this.status = status;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Métodos para obter data e hora de forma separada
    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataHora.format(formatter);
    }

    public String getHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dataHora.format(formatter);
    }

    // Método toString para exibir o agendamento
    public String toString() {
        return "Agendamento{" +
               "id=" + id +
               ", dataHora=" + dataHora +
               ", cliente='" + cliente + '\'' +
               ", servico='" + servico + '\'' +
               ", status='" + status + '\'' +
               '}';
    }
}
