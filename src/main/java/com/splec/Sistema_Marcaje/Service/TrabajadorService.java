package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Model.Trabajador;
import com.splec.Sistema_Marcaje.Repository.ITrabajadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabajadorService implements ITrabajadorService {

    public final ITrabajadorRepository iTrabajadorRepository;

    public TrabajadorService(ITrabajadorRepository iTrabajadorRepository) {
        this.iTrabajadorRepository = iTrabajadorRepository;
    }

    @Override
    public List<Trabajador> listaTrabajadores() {
        List<Trabajador>listaTrabajadores = iTrabajadorRepository.findAll();
        return listaTrabajadores;
    }

    @Override
    public void saveTrabajador(Trabajador trabajador) {
        iTrabajadorRepository.save(trabajador);
    }


    @Override
    public Trabajador buscarTrabajadorPorId(Long id) {
        Trabajador trabajador = iTrabajadorRepository.findById(id).orElse(null);
        return trabajador;
    }

    @Override
    public void eliminarTrabajadorPorId(Long id) {
        iTrabajadorRepository.deleteById(id);

    }
}
