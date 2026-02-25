package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Dto.MarcajeDTO;
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
    public MarcajeDTO registrarEntrada(Long trabajadorId) {

        //metodo que hace que no se repita un marcaje
        Optional<Marcaje> marcajeAbierto =
                iMarcajeRepository.findTopByTrabajador_IdAndHoraSalidaIsNullOrderByFechaDesc(trabajadorId);

        if (marcajeAbierto.isPresent()) {
            throw new RuntimeException("El trabajador ya tiene una entrada sin salida");
        }

        Trabajador trabajador = iTrabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        //si el metodo esta abierto ejecutamos el nuevo marcaje en base al setTrabajador
        Marcaje marcaje = new Marcaje();
        marcaje.setFecha(LocalDate.now());
        marcaje.setHoraEntrada(LocalTime.now());
        marcaje.setHoraSalida(null);
        marcaje.setTrabajador(trabajador);

        //guardamos el marcaje nuevo en marcajeguardado
        Marcaje marcajeGuardado = iMarcajeRepository.save(marcaje);

        //lo convertimos a dto con sus parametros
        return convertirADTO(marcajeGuardado);
    }
    @Override
    public MarcajeDTO convertirADTO(Marcaje marcaje) {

        MarcajeDTO dto = new MarcajeDTO();
        dto.setId(marcaje.getId());
        dto.setFecha(marcaje.getFecha());
        dto.setHoraEntrada(marcaje.getHoraEntrada());
        dto.setHoraSalida(marcaje.getHoraSalida());
        dto.setMinutosTrabajados(marcaje.getMinutosTrabajados());
        dto.setTrabajadorId(marcaje.getTrabajador().getId());
        dto.setNombreTrabajador(marcaje.getTrabajador().getNombre());

        return dto;
    }

    @Override
    public MarcajeDTO registrarSalida(Long trabajadorId) {

        Marcaje marcaje = iMarcajeRepository
                .findTopByTrabajador_IdAndHoraSalidaIsNullOrderByFechaDesc(trabajadorId)
                .orElseThrow(() -> new RuntimeException("No hay marcaje abierto"));

        LocalTime horaSalida = LocalTime.now();
        marcaje.setHoraSalida(horaSalida);

        Duration duracion = Duration.between(marcaje.getHoraEntrada(), horaSalida);
        long minutos = duracion.toMinutes();
        marcaje.setMinutosTrabajados(minutos);

        Marcaje marcajeGuardado = iMarcajeRepository.save(marcaje);

        MarcajeDTO dto = new MarcajeDTO();
        dto.setId(marcajeGuardado.getId());
        dto.setFecha(marcajeGuardado.getFecha());
        dto.setHoraEntrada(marcajeGuardado.getHoraEntrada());
        dto.setHoraSalida(marcajeGuardado.getHoraSalida());
        dto.setMinutosTrabajados(marcajeGuardado.getMinutosTrabajados());
        dto.setTrabajadorId(marcajeGuardado.getTrabajador().getId());
        dto.setNombreTrabajador(marcajeGuardado.getTrabajador().getNombre());

        return dto;
    }
}
