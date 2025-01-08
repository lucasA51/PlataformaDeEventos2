// Função para buscar eventos cadastrados
function fetchEventos() {
  fetch("/api/eventos")
    .then((response) => response.json())
    .then((data) => {
      const eventList = document.getElementById("eventList");
      eventList.innerHTML = "";

      data.forEach((event) => {
        const eventItem = document.createElement("div");
        eventItem.classList.add("event-item");

        eventItem.innerHTML = `
          <div class="event-image">
              <img src="${event.imagem}" alt="Imagem do Evento">
          </div>
          <div class="event-info">
              <h4>${event.titulo}</h4>
              <p><strong>Data:</strong> ${formatDate(
                event.dataInicio.split('T')[0]
              )} ${formatTime(event.dataInicio.split('T')[1])}</p>
              <p><strong>Local:</strong> ${event.localEvento}</p>
              <button onclick="deleteEvento(${event.id})">Deletar</button>
              <button onclick="editEvento(${event.id})">Alterar</button>
          </div>
        `;
        eventList.appendChild(eventItem);
      });
    })
    .catch((error) => {
      console.error("Erro ao buscar eventos:", error);
    });
}

// Funções de formatação de data e hora
function formatDate(dateString) {
  const options = { year: "numeric", month: "2-digit", day: "2-digit" };
  return new Date(dateString).toLocaleDateString("pt-BR", options);
}

function formatTime(timeString) {
  if (!timeString) {
    return "00:00"; // Retorna uma string vazia ou um valor padrão se timeString for null ou undefined
  }
  return timeString.slice(0, 5); // Formato HH:mm
}

// Função para deletar um evento
function deleteEvento(eventId) {
  fetch(`/api/eventos/${eventId}`, { method: "DELETE" })
    .then((response) => {
      if (response.ok) {
        alert("Evento deletado com sucesso!");
        fetchEventos();
      } else {
        alert("Erro ao deletar o evento.");
      }
    })
    .catch((error) => {
      alert("Erro ao deletar evento: " + error);
    });
}

/*// Função para redirecionar para a página de edição
function editEvento(eventId) {
  window.location.href = `/editEvento?id=${eventId}`;
}*/

// Função para alterar um evento
function editEvento(eventId) {
    // Coleta os novos dados do evento (pode ser via formulário ou prompt)
    const novoTitulo = prompt("Digite o novo título do evento:");
    const novoLocal = prompt("Digite o novo local do evento:");
    const novaDataInicio = prompt("Digite a nova data de início (AAAA-MM-DDTHH:mm):");
    const novaDataFim = prompt("Digite a nova data de fim (AAAA-MM-DDTHH:mm):");
    const novaImagem = prompt("Digite a nova URL da imagem do evento:");
    const novaDescricao = prompt("Digite a nova descrição do evento:");

    if (!novoTitulo || !novoLocal || !novaDataInicio || !novaDataFim || !novaImagem || !novaDescricao) {
        alert("Todos os campos são obrigatórios!");
        return;
    }

    const eventoAtualizado = {
        titulo: novoTitulo,
        localEvento: novoLocal,
        dataInicio: novaDataInicio,
        dataFim: novaDataFim,
        imagem: novaImagem,
        descricao: novaDescricao,
    };

    fetch(`/api/eventos/${eventId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(eventoAtualizado),
    })
        .then((response) => {
            if (response.ok) {
                alert("Evento alterado com sucesso!");
                fetchEventos(); // Atualiza a lista de eventos
            } else {
                alert("Erro ao alterar o evento.");
            }
        })
        .catch((error) => {
            alert("Erro ao alterar evento: " + error);
        });
}


// Envio do formulário para criação do evento
document
  .getElementById("eventForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();

    const evento = {
      titulo: document.getElementById("titulo").value,
      imagem: document.getElementById("imagem").value,
      descricao: document.getElementById("descricao").value,
      dataInicio: document.getElementById("dataInicio").value,
      dataFim: document.getElementById("dataFim").value, // Verificar valor
      localEvento: document.getElementById("localEvento").value,
    };

    console.log("Evento para enviar:", evento); // Log para ver o que está sendo enviado

    fetch("/api/eventos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(evento),
    })
      .then((response) => response.json())
      .then((data) => {
        alert("Evento criado com sucesso!");
        fetchEventos();
      })
      .catch((error) => {
        alert("Erro ao criar evento: " + error);
      });
  });

// Carregar eventos ao carregar a página
window.onload = function () {
  fetchEventos();
};
