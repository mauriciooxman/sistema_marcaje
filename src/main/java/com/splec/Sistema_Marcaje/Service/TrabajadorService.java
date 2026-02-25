package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Dto.MarcajeDTO;
import com.splec.Sistema_Marcaje.Dto.TrabajadorDTO;
import com.splec.Sistema_Marcaje.Model.Marcaje;
import com.splec.Sistema_Marcaje.Model.Trabajador;
import com.splec.Sistema_Marcaje.Repository.ITrabajadorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrabajadorService implements ITrabajadorService {

    public final ITrabajadorRepository iTrabajadorRepository;

    public TrabajadorService(ITrabajadorRepository iTrabajadorRepository) {
        this.iTrabajadorRepository = iTrabajadorRepository;
    }

    @Override
    public List<TrabajadorDTO> listaTrabajadores() {

        List<Trabajador> listaTrabajadores = iTrabajadorRepository.findAll();
        List<TrabajadorDTO> listaDto = new ArrayList<>();

        for (Trabajador trabajador : listaTrabajadores) {
            listaDto.add(convertirADTO(trabajador));
        }

        return listaDto;
    }
    @Override
    public TrabajadorDTO convertirADTO(Trabajador trabajador) {
        TrabajadorDTO dto = new TrabajadorDTO();
        dto.setId(trabajador.getId());
        dto.setNombre(trabajador.getNombre());
        dto.setApellido(trabajador.getApellido());

        return dto;
    }

    @Override
    public void saveTrabajador(Trabajador trabajador) {
        iTrabajadorRepository.save(trabajador);
    }


    @Override
    public TrabajadorDTO buscarTrabajadorPorId(Long id) {
        Trabajador trabajador = iTrabajadorRepository.findById(id).orElse(null);
        return convertirADTO(trabajador);
    }

    @Override
    public void eliminarTrabajadorPorId(Long id) {
        iTrabajadorRepository.deleteById(id);

    }
}
