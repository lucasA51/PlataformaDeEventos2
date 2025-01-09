package org.example.plataformadeeventos.service;

import org.example.plataformadeeventos.exception.EventoNotFoundException;
import org.example.plataformadeeventos.model.Evento;
import org.example.plataformadeeventos.model.Usuario;
import org.example.plataformadeeventos.repository.EventoRepository;
import org.example.plataformadeeventos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento inserir(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento alterar(Long id, Evento evento) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNotFoundException(id));

        eventoExistente.setTitulo(evento.getTitulo());
        eventoExistente.setImagem(evento.getImagem());
        eventoExistente.setDescricao(evento.getDescricao());
        eventoExistente.setDataInicio(evento.getDataInicio());
        eventoExistente.setDataFim(evento.getDataFim());
        eventoExistente.setLocalEvento(evento.getLocalEvento());

        return eventoRepository.save(eventoExistente);
    }

    public void excluir(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNotFoundException(id));

        eventoRepository.delete(evento);
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + id));
    }


    public void inscreverUsuarioNoEvento(Long eventoId, String emailUsuario) {
        // Buscar o evento pelo ID
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        // Buscar o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verificar se o usuário já está inscrito
        if (evento.getUsuarios().contains(usuario)) {
            throw new RuntimeException("Usuário já inscrito neste evento");
        }

        // Inscrever o usuário no evento
        evento.getUsuarios().add(usuario);
        usuario.getEventos().add(evento);

        // Salvar as alterações
        eventoRepository.save(evento);
        usuarioRepository.save(usuario);
    }

}
