// Função para obter o ID do evento da URL
function getEventId() {
  const path = window.location.pathname;

  const parts = path.split('/');
  const id = parts[parts.length - 1];
  console.log(id);

  return id;
}

// Função para buscar os detalhes do evento
function fetchEventDetails(eventId) {
  fetch(`/api/eventos/${eventId}`)
    .then((response) => response.json())
    .then((event) => {
      const eventDetailsContainer = document.getElementById("eventDetails");

      // Estrutura HTML para exibir os detalhes do evento
      eventDetailsContainer.innerHTML = `
                <h1>${event.titulo}</h1>
                <p><strong>Data:</strong> ${formatDate(event.dataInicio.split('T')[0])}</p>
                <p><strong>Hora:</strong> ${formatTime(event.dataInicio.split('T')[1])}</p>
                <p><strong>Local:</strong> ${event.localEvento}</p>
                <img src="${
                  event.imagem
                }" alt="Imagem do Evento" class="event-image">
                <div class="event-description">
                    <p>${event.descricao}</p>
                </div>
                <button class="subscribe-button">Inscreva-se no Evento</button>
            `;
    })
    .catch((error) => {
      console.error("Erro ao buscar detalhes do evento:", error);
    });
}

// Função para formatar a data no formato desejado (dd/mm/yyyy)
function formatDate(dateString) {
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();

  return `${day}/${month}/${year}`;
}

// Função para formatar a hora no formato desejado (HH:mm)
function formatTime(timeString) {
  return timeString.slice(0, 5); // Formato HH:mm
}

// Ao carregar a página, obtemos o ID do evento e buscamos seus detalhes
window.onload = function () {
  const eventId = getEventId();
  if (eventId) {
    fetchEventDetails(eventId);
  } else {
    console.error("ID do evento não encontrado na URL");
  }
};
