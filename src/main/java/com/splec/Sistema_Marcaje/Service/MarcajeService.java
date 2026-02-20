package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Model.Marcaje;
import com.splec.Sistema_Marcaje.Model.Trabajador;
import com.splec.Sistema_Marcaje.Repository.IMarcajeRepository;
import com.splec.Sistema_Marcaje.Repository.ITrabajadorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class MarcajeService implements IMarcajeService {
    public final IMarcajeRepository iMarcajeRepository;
    public final ITrabajadorRepository iTrabajadorRepository;

    public MarcajeService(IMarcajeRepository iMarcajeRepository, ITrabajadorRepository iTrabajadorRepository) {
        this.iMarcajeRepository = iMarcajeRepository;
        this.iTrabajadorRepository = iTrabajadorRepository;
    }

    @Override
    public List<Marcaje> listaMarcajes() {
        List<Marcaje> traerListaMarcajes = iMarcajeRepository.findAll();
        return traerListaMarcajes;
    }

    @Override
    public void guardarMarcajes(Marcaje marcaje) {
        iMarcajeRepository.save(marcaje);
    }

    @Override
    public Marcaje buscarMarcajePorId(Long id) {
        Marcaje buscarMarcajePorId = iMarcajeRepository.findById(id).orElse(null);
        return buscarMarcajePorId;
    }

    @Override
    public void eliminarMarcajePorId(Long id) {
        iMarcajeRepository.deleteById(id);

    }

    @Override
    public Marcaje registrarEntrada(Long trabajadorId) {
        Marcaje marcaje = new Marcaje();
        marcaje.setFecha(LocalDate.now());
        marcaje.setHoraEntrada(LocalTime.now());
        marcaje.setHoraSalida(null);

        Trabajador trabajador = iTrabajadorRepository.findById(trabajadorId)
                .orElseThrow(()-> new RuntimeException("Trabajador No Encontrado"));
        marcaje.setTrabajador(trabajador);
        return iMarcajeRepository.save(marcaje);
    }

    @Override
    public Marcaje registrarSalida(Long trabajadorId) {
        Marcaje marcaje = iMarcajeRepository.findTopByTrabajadorIdAndHoraSalidaIsNullOrderByFechaDesc(trabajadorId)
                .orElseThrow(()-> new RuntimeException("No Hay Marcaje Abierto"));
        marcaje.setHoraSalida(LocalTime.now());
        return iMarcajeRepository.save(marcaje);
    }
}
