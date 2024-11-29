package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
    // Método para verificar horários disponíveis para um barbeiro e data específica

        public List<String> getHorariosDisponiveis(int idBarbeiro, java.sql.Date data) {
            List<String> horariosDisponiveis = new ArrayList<>();
            String sql = "SELECT horario_agendamento FROM Agendamentos "
                       + "WHERE id_profissional = ? AND DATE(horario_agendamento) = ? "
                       + "AND status_agenda = 'Confirmado'"; // Ajuste para considerar apenas horários confirmados
            
            try (Connection conn = ConectaDB.getConnection();
                 PreparedStatement comandaSQÇ = conn.prepareStatement(sql)) {
                
                comandaSQÇ.setInt(1, idBarbeiro);
                comandaSQÇ.setDate(2, data); // Passando java.sql.Date para o PreparedStatement
                
                ResultSet pegaInfo = comandaSQÇ.executeQuery();
                while (pegaInfo.next()) {
                    String horario = pegaInfo.getString("horario_agendamento");
                    horariosDisponiveis.add(horario); // Adiciona o horário disponível à lista
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return horariosDisponiveis;
        }

    // Método para agendar um novo serviço
    public boolean agendaresultadoPesquisaervico(int idCliente, int idBarbeiro, int idServico, Timestamp horarioAgendamento) {
        String comandoSQL = "INSERT INTO Agendamentos (Horario_Agendamento, Status_Agenda, Id_Cliente, Id_Profissional, Id_Servico) " + "VALUES (?, 'Pendente', ?, ?, ?)";

        try (Connection connect = ConectaDB.getConnection();
             PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)) {

            // Define os parâmetros da consulta
            comandaSQL.setTimestamp(1, horarioAgendamento);
            comandaSQL.setInt(2, idCliente);
            comandaSQL.setInt(3, idBarbeiro);
            comandaSQL.setInt(4, idServico);

            // Executa a consulta
            int consultaLinha = comandaSQL.executeUpdate();
            return consultaLinha > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getIdBarbeiroPeloNome(String nomeBarbeiro) {

        int idBarbeiro = 0;
        
        String comandoSQL = "SELECT Id_Profissional FROM Barbeiros WHERE Nome_Barbeiro = ?";
        
        try (Connection connect = ConectaDB.getConnection();PreparedStatement comandaSQÇ = connect.prepareStatement(comandoSQL)) {
            comandaSQÇ.setString(1, nomeBarbeiro);
            
            ResultSet pegaInfo = comandaSQÇ.executeQuery();
            if (pegaInfo.next()) {
                idBarbeiro = pegaInfo.getInt("Id_Profissional");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return idBarbeiro;
    }
}
