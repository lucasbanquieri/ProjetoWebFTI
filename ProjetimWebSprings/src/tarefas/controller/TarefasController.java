package tarefas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import tarefas.dao.JdbcTarefaDao;
import tarefas.model.Tarefa;

@Controller
public class TarefasController {
	@RequestMapping("/novaTarefa")
	public String form() {
	return "tarefas/formulario";
	}
	
	@RequestMapping("/adicionaTarefa")
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		if (tarefa.getDescricao() == null || tarefa.getDescricao().equals("") || result.hasFieldErrors("descricao")) {
			System.out.println("Campo descrição não pode estar vazio.");
			return "tarefas/formulario";
		} else {
			tarefa.setDataFinalizacao(null);
			tarefa.setFinalizado(false);
			dao.adiciona(tarefa);
			return "tarefas/tarefa-adicionada";
		}
	}
	
	@RequestMapping("/listaTarefas")
	public String lista(Model model) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		List<Tarefa> tarefas = dao.listarTarefas();
		model.addAttribute("tarefas", tarefas);
		return "tarefas/lista";
	}
	
	@RequestMapping("/removeTarefa")
	public String remove(Tarefa tarefa, Model model) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.excluirTarefa(tarefa);
		List<Tarefa> tarefas = dao.listarTarefas();
		model.addAttribute("tarefas", tarefas);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("/mostraTarefa")
	public String mostra(Long id, Model model) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		model.addAttribute("tarefa", dao.buscaTarefa(id));
		return "tarefas/mostra";
	}
	
	@RequestMapping("/alteraTarefa")
	public String altera(Tarefa tarefa) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.alterarTarefa(tarefa);
		return "redirect:listaTarefas";
	}
	
	@RequestMapping("/finalizaTarefa")
	public String finaliza(Long id, Model model) {
		JdbcTarefaDao dao = new JdbcTarefaDao();
		dao.finaliza(id);
		model.addAttribute("tarefa", dao.buscaTarefa(id));
		return "tarefas/finalizada";
	}
}
