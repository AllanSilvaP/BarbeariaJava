package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Agendamento;

public class AgendaDAO {
    // Método para verificar horários disponíveis para um barbeiro e data específica

    public List<String> getHorariosDisponiveis(int idBarbeiro, java.sql.Date data) {
        List<String> horariosDisponiveis = new ArrayList<>();
        String sql = "SELECT horario_agendamento FROM Agendamentos "
                + "WHERE id_profissional = ? AND DATE(horario_agendamento) = ? "
                + "AND status_agenda = 'Confirmado'"; // Considerando apenas horários confirmados

        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement comandaSQL = connect.prepareStatement(sql)) {

            comandaSQL.setInt(1, idBarbeiro);
            comandaSQL.setDate(2, data); // Passando no formato date do sql para o PreparedStatement

            ResultSet rs = comandaSQL.executeQuery();
            while (rs.next()) {
                String horario = rs.getString("horario_agendamento");
                horariosDisponiveis.add(horario); // Adiciona o horário disponível à lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horariosDisponiveis;
    }

    // Método para agendar um novo serviço
    public boolean agendarServico(int idCliente, int idBarbeiro, int idServico, Timestamp horarioAgendamento) {
        String sql = "INSERT INTO Agendamentos (Horario_Agendamento, Status_Agenda, Id_Cliente, Id_Profissional, Id_Servico) "
                + "VALUES (?, 'Pendente', ?, ?, ?)";

        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement comandaSQL = connect.prepareStatement(sql)) {

            comandaSQL.setTimestamp(1, horarioAgendamento);
            comandaSQL.setInt(2, idCliente);
            comandaSQL.setInt(3, idBarbeiro);
            comandaSQL.setInt(4, idServico);

            int rowsAffected = comandaSQL.executeUpdate();
            return rowsAffected > 0; // Retorna true se conseguiu inserir no banco de dados
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para alterar o status de um agendamento (ex: de 'Pendente' para
    // 'Confirmado')
    public boolean alterarStatusAgendamento(int idAgendamento, String novoStatus) {
        String comandoSQL = "UPDATE Agendamentos SET Status_agenda = ? WHERE Id_agendamento = ?";

        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement comandaSQL = connect.prepareStatement(comandoSQL)) {

            comandaSQL.setString(1, novoStatus);
            comandaSQL.setInt(2, idAgendamento);

            int rowsAffected = comandaSQL.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para listar todos os agendamentos
    public List<Agendamento> listarAgendamentos() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT * FROM Agendamentos";

        try (Connection conn = ConectaDB.getConnection();
                PreparedStatement comandaSQL = conn.prepareStatement(sql);
                ResultSet rs = comandaSQL.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_agendamento");
                Timestamp dataHoraTimestamp = rs.getTimestamp("horario_agendamento"); // Recebe o timestamp diretamente
                String status = rs.getString("status_agenda");
                int idCliente = rs.getInt("id_cliente");
                int idProfissional = rs.getInt("id_profissional");
                int idServico = rs.getInt("id_servico");

                // Usando LocalDateTime ao invés de String para a dataHora
                LocalDateTime dataHora = dataHoraTimestamp.toLocalDateTime(); // Converte o Timestamp para LocalDateTime

                // Criando o Agendamento com o LocalDateTime
                Agendamento agendamento = new Agendamento(id, dataHora, String.valueOf(idCliente),
                        String.valueOf(idServico), status);
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }

    public Agendamento buscarAgendamentoPorId(int id) {
        String comandoSQL = "SELECT * FROM Agendamentos WHERE Id_agendamento = ?";
        try (Connection connect = ConectaDB.getConnection();
                PreparedStatement ps = connect.prepareStatement(comandoSQL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Obter o Timestamp diretamente
                Timestamp timestamp = rs.getTimestamp("horario_agendamento");

                // Converter Timestamp para LocalDateTime
                LocalDateTime dataHora = timestamp.toLocalDateTime();

                // Criar o Agendamento
                return new Agendamento(
                        rs.getInt("Id_Agendamento"),
                        dataHora, // Agora passando LocalDateTime para o construtor
                        rs.getString("cliente"),
                        rs.getString("servico"),
                        rs.getString("status_agenda") // Supondo que o status esteja na coluna "status_agenda"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para alterar um agendamento
    public void alterarAgendamento(int idAgendamento, int idCliente, int idServico, LocalDateTime novoHorario,
            String novoStatus) {
        String comandoSQL = "UPDATE Agendamentos SET ID_Cliente = ?, ID_Servico = ?, Horario_Agendamento = ?, Status_Agenda = ? WHERE Id_Agendamento = ?";

        try (Connection conn = ConectaDB.getConnection(); PreparedStatement ps = conn.prepareStatement(comandoSQL)) {

            // Converte LocalDateTime para Timestamp
            Timestamp timestamp = Timestamp.valueOf(novoHorario);

            // Setando os valores a serem att
            ps.setInt(1, idCliente); 
            ps.setInt(2, idServico);
            ps.setTimestamp(3, timestamp);
            ps.setString(4, novoStatus);
            ps.setInt(5, idAgendamento);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um agendamento
    public void excluirAgendamento(int id) {
        String sql = "DELETE FROM Agendamentos WHERE Id_Agendamento = ?";
        try (Connection conn = ConectaDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obterIdBarbeiroPorNome(String nomeBarbeiro) {
        String sql = "SELECT Id_profissional FROM Barbeiros WHERE Nome_Barbeiro = ?";
        try (Connection conn = ConectaDB.getConnection();
                PreparedStatement comandaSQL = conn.prepareStatement(sql)) {
            comandaSQL.setString(1, nomeBarbeiro);
            ResultSet rs = comandaSQL.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_Profissional");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Se der ruim sai -1
    }

    public int obterIdClientePorNome(String nomeCliente) {
        String sql = "SELECT Id_Cliente FROM Clientes WHERE Nome = ?";
        try (Connection conn = ConectaDB.getConnection();
                PreparedStatement comandaSQL = conn.prepareStatement(sql)) {
            comandaSQL.setString(1, nomeCliente);
            ResultSet rs = comandaSQL.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_Cliente"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Se der ruim sai -1
    }

    public int obterIdServicoPorNome(String nomeServico) {
        String sql = "SELECT Id_Servico FROM Servicos WHERE Nome_servico = ?";
        try (Connection conn = ConectaDB.getConnection();
                PreparedStatement comandaSQL = conn.prepareStatement(sql)) {
            comandaSQL.setString(1, nomeServico);
            ResultSet rs = comandaSQL.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_Servico"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Se der ruim sai -1
    }

}
