package tarefas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import tarefas.model.Usuario;

public class JdbcUsuarioDao {
	BancoDados db = null;
	
	public JdbcUsuarioDao() {
		try {
			db = new BancoDados("curso_javaweb");
		} catch (NamingException e) {
			System.out.println("Erro ao instanciar o Banco de Dados: " + e);
		}
	}
	
	public boolean existeUsuario(Usuario usuario) {
		boolean localizado = false;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();

			String sql = "SELECT *"
					+ " FROM  usuarios"
					+ " WHERE login = ? AND senha = ?;";

			stmt = conn.prepareStatement(sql.toString());
			
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());

			rs = stmt.executeQuery();
			
			if (rs.next()) {
				localizado = true;
			} else {
				localizado = false;
			}

		} catch (SQLException e) {
			System.out.println("Erro no método buscarTarefa");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
		return localizado;
	}
}
