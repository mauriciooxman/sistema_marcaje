package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Dto.MarcajeDTO;
import com.splec.Sistema_Marcaje.Model.Marcaje;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMarcajeService {
    public List<Marcaje> listaMarcajes();
    public void guardarMarcajes(Marcaje marcaje);
    public Marcaje buscarMarcajePorId(Long id);
    public void eliminarMarcajePorId(Long id);
    public MarcajeDTO registrarEntrada(Long trabajadorId);
    public MarcajeDTO registrarSalida(Long trabajadorId);
    public MarcajeDTO convertirADTO(Marcaje marcaje);
}
