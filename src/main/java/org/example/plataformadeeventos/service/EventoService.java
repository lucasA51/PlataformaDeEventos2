package org.example.plataformadeeventos.service;

import org.example.plataformadeeventos.exception.EventoNotFoundException;
import org.example.plataformadeeventos.model.Evento;
import org.example.plataformadeeventos.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

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

}
