package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Model.Marcaje;
import com.splec.Sistema_Marcaje.Model.Trabajador;
import com.splec.Sistema_Marcaje.Repository.IMarcajeRepository;
import com.splec.Sistema_Marcaje.Repository.ITrabajadorRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

        // 🔎 Verificamos si ya tiene un marcaje abierto
        Optional<Marcaje> marcajeAbierto =
                iMarcajeRepository.findTopByTrabajador_IdAndHoraSalidaIsNullOrderByFechaDesc(trabajadorId);

        if (marcajeAbierto.isPresent()) {
            throw new RuntimeException("El trabajador ya tiene una entrada sin salida");
        }

        Trabajador trabajador = iTrabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Marcaje marcaje = new Marcaje();
        marcaje.setFecha(LocalDate.now());
        marcaje.setHoraEntrada(LocalTime.now());
        marcaje.setHoraSalida(null);
        marcaje.setTrabajador(trabajador);

        return iMarcajeRepository.save(marcaje);
    }

    @Override
    public Marcaje registrarSalida(Long trabajadorId) {

        Marcaje marcaje = iMarcajeRepository
                .findTopByTrabajador_IdAndHoraSalidaIsNullOrderByFechaDesc(trabajadorId)
                .orElseThrow(() -> new RuntimeException("No hay marcaje abierto"));

        LocalTime horaSalida = LocalTime.now();
        marcaje.setHoraSalida(horaSalida);

        // ⏱️ Calcular tiempo trabajado
        Duration duracion = Duration.between(marcaje.getHoraEntrada(), horaSalida);
        long minutos = duracion.toMinutes();

        marcaje.setMinutosTrabajados(minutos);

        return iMarcajeRepository.save(marcaje);
    }
}
