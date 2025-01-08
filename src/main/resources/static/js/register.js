// Envio do formulário para criação de um usuário
document
    .getElementById("loginForm")
    .addEventListener("submit", function (event) {
        event.preventDefault();

        const usuario = {
            email: document.getElementById("email").value,
            nome: document.getElementById("fullname").value,
            senha: document.getElementById("password").value,
            administrador: true
        };

        console.log("Usuário para enviar:", usuario); // Log para ver o que está sendo enviado

        fetch("/users", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(usuario),
        })
            .then((response) => response.json())
            .then((data) => {
                alert("Usuário criado com sucesso!");
            })
            .catch((error) => {
                alert("Erro ao criar usuário: " + error);
            });
    });