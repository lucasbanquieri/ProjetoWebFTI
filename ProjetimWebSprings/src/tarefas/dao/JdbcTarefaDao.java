package tarefas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import tarefas.model.Tarefa;

public class JdbcTarefaDao {
	BancoDados db = null;
	
	public JdbcTarefaDao() {
		try {
			db = new BancoDados("curso_javaweb");
		} catch (NamingException e) {
			System.out.println("Erro ao instanciar o Banco de Dados: " + e);
		}
	}

	public void adiciona(Tarefa tarefa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();
			conn.setAutoCommit(false);
			//java.sql.Date d = new java.sql.Date(tarefa.getDataFinalizacao().getTime());

			StringBuffer sql = new StringBuffer();
			
			sql.append("INSERT INTO  tarefas (descricao, finalizado)");
			sql.append("VALUES(?, ?)");

			stmt = conn.prepareStatement(sql.toString());

			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());

			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println("Erro no método adiciona. - rollback");
				}
			}
			System.out.println("Erro no método adiciona.");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
		
		System.out.println("");
		
	}
	
	public List<Tarefa> listarTarefas() {
		ArrayList<Tarefa> arrayTask = new ArrayList<Tarefa>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();

			String sql = "SELECT id, descricao, finalizado, dataFinalizacao"
					+ " FROM  tarefas"
					+ " ORDER BY id ASC";

			stmt = conn.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

			while (rs.next()) {
				Tarefa task = new Tarefa();
				
				task.setId(rs.getLong(1));
				task.setDescricao(rs.getString(2));
				task.setFinalizado(rs.getBoolean(3));
				if (task.isFinalizado()) {
					task.setDataFinalizacao(new Date(rs.getTimestamp("dataFinalizacao").getTime()));
				}	
				
				arrayTask.add(task);
			}

		} catch (SQLException e) {
			System.out.println("Erro no método listarTarefas");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
		return arrayTask;
	}
	
	public void excluirTarefa(Tarefa tarefa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();
			conn.setAutoCommit(false);
			//java.sql.Date d = new java.sql.Date(aluno.getDataNascimento().getTime());

			StringBuffer sql = new StringBuffer();
			
			sql.append("DELETE FROM tarefas ");
			sql.append("WHERE id = ?;");

			stmt = conn.prepareStatement(sql.toString());

			stmt.setLong(1, tarefa.getId());

			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println("Erro no método excluirTarefa - rollback");
				}
			}
			System.out.println("Erro no método excluirTarefa");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
	}
	
	public Tarefa buscaTarefa(Long id) {
		Tarefa tarefa = new Tarefa();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();

			String sql = "SELECT descricao, finalizado, dataFinalizacao"
					+ " FROM  tarefas"
					+ " WHERE id = ?;";

			stmt = conn.prepareStatement(sql.toString());
			
			stmt.setLong(1, id);

			rs = stmt.executeQuery();

			while (rs.next()) {
				tarefa.setId(id);
				tarefa.setDescricao(rs.getString(1));
				tarefa.setFinalizado(rs.getBoolean(2));
				if (tarefa.isFinalizado()) {
					tarefa.setDataFinalizacao(new Date(rs.getTimestamp("dataFinalizacao").getTime()));
				}
				
			}

		} catch (SQLException e) {
			System.out.println("Erro no método buscarTarefa");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
		return tarefa;
	}
	
	public void alterarTarefa(Tarefa tarefa) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();
			conn.setAutoCommit(false);
			java.sql.Date d = new java.sql.Date(tarefa.getDataFinalizacao().getTime());

			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE tarefas SET descricao = ?, finalizado = ?, dataFinalizacao = ? ");
			sql.append("WHERE id = ?;");

			stmt = conn.prepareStatement(sql.toString());

			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			stmt.setDate(3, d);
			stmt.setLong(4, tarefa.getId());

			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println("Erro no método alterarTarefa - rollback");
				}
			}
			System.out.println("Erro no método alterarTarefa");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
	}
	
	public void finaliza(Long id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.obterConexao();
			conn.setAutoCommit(false);
			Date data = new Date();
			java.sql.Date date = new java.sql.Date(data.getTime());

			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE tarefas SET finalizado = ?, dataFinalizacao = ? ");
			sql.append("WHERE id = ?;");

			stmt = conn.prepareStatement(sql.toString());

			stmt.setBoolean(1, true);
			stmt.setDate(2, date);
			stmt.setLong(3, id);

			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					System.out.println("Erro no método finaliza - rollback");
				}
			}
			System.out.println("Erro no método finaliza");
			e.printStackTrace();
		} finally {
			db.finalizaObjetos(rs, stmt, conn);
		}
	}
}
