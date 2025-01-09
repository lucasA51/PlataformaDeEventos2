// Função para buscar os eventos do usuário logado
function fetchMyEvents() {
    fetch("/users/api/meus-eventos")
        .then((response) => {
            if (!response.ok) {
                throw new Error("Falha ao buscar eventos do usuário");
            }
            return response.json();
        })
        .then((data) => {
            const myEventsContainer = document.getElementById("myEvents");
            myEventsContainer.innerHTML = "";

            if (data.length === 0) {
                myEventsContainer.innerHTML = "<p>Você ainda não está inscrito em nenhum evento.</p>";
                return;
            }

            data.forEach((event) => {
                const eventCard = document.createElement("div");
                eventCard.classList.add("event-card");

                eventCard.innerHTML = `
                    <div class="event-image">
                        <img src="${event.imagem}" alt="Imagem do Evento">
                    </div>
                    <div class="event-info">
                        <h3>${event.titulo}</h3>
                        <p><strong>Data:</strong> ${formatDate(event.dataInicio)} - ${formatTime(event.dataFim)}</p>
                        <p><strong>Local:</strong> ${event.localEvento}</p>
                        <a href="/evento/${event.id}" class="btn-primary">Ver Detalhes</a>
                    </div>
                `;

                myEventsContainer.appendChild(eventCard);
            });
        })
        .catch((error) => {
            console.error("Erro ao buscar eventos do usuário:", error);
            const myEventsContainer = document.getElementById("myEvents");
            myEventsContainer.innerHTML = "<p>Erro ao carregar os eventos. Tente novamente mais tarde.</p>";
        });
}

// Formata a hora para (HH:mm)
function formatTime(timeString) {
    if (!timeString) return "00:00";
    return timeString.slice(0, 5);
}

// Formata a data no formato 'dd/mm/yyyy'
function formatDate(dateString) {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}

// Carrega os eventos ao carregar a página
window.onload = function () {
    fetchMyEvents();
};