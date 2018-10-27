<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="css/formStyle.css">
			<title>Insert title here</title>
	</head>
	<style>
		
	</style>
	<body>
		<h3>Adicionar Tarefas</h3>
		<form:errors path="tarefa.descricao"/>
		<form action="adicionaTarefa" method="post">
			Descrição: <br />
			<textarea class="descricao" placeholder="Insira a descrição da tarefa aqui. (minimo 5 caracteres)" minlength="5" name="descricao" rows="5" cols="100"></textarea><br />
			<button id="cadastrar" disabled>Adicionar</button>
		</form>
		<script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
		<script>
			$(".descricao").on("input", function(){
				if ($(this).val().length < 5) {
					$(this).css("border", "1px solid red");
					$("#cadastrar").attr("disabled", "disabled");
				} else {
					$(this).css("border", "1px solid green");
					$("#cadastrar").removeAttr("disabled");
				}
			})
		</script>
	</body>
</html>