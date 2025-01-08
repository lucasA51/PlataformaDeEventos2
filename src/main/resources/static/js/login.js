document.addEventListener("DOMContentLoaded", function () {
	    const loginForm = document.querySelector("form");

	    loginForm.addEventListener("submit", async function (event) {
	    event.preventDefault(); // Impede o envio padrão do formulário

	    // Captura os valores dos campos
	    const email = document.getElementById("username").value;
	    const senha = document.getElementById("password").value;

	    // Valida se os campos estão preenchidos
	    if (!email || !senha) {
	    alert("Por favor, preencha o usuário (email) e senha.");
	    return;
	    }

	try {
	    const response = await fetch('/login', {
	    method: 'POST',
	    headers: {
	    'Content-Type': 'application/json'
	},
	    body: JSON.stringify({ email, senha })
	});

	    if (!response.ok) {
	    throw new Error('Falha no login. Verifique suas credenciais.');
	}

	    // Redireciona para a página inicial após o login bem-sucedido
	    window.location.href = '/home';
	} catch (error) {
	    console.error('Erro:', error);
	    alert(error.message || 'Erro ao tentar fazer login.');
	}
	});
});
