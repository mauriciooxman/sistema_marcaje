package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Dto.TrabajadorDTO;
import com.splec.Sistema_Marcaje.Model.Trabajador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITrabajadorService {
    public List<TrabajadorDTO> listaTrabajadores();
    public void saveTrabajador(Trabajador trabajador);
    public TrabajadorDTO buscarTrabajadorPorId(Long id);
    public void eliminarTrabajadorPorId(Long id);
    public TrabajadorDTO convertirADTO(Trabajador trabajador);
}
