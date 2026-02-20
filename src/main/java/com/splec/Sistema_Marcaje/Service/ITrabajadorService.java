package com.splec.Sistema_Marcaje.Service;

import com.splec.Sistema_Marcaje.Model.Trabajador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITrabajadorService {
    public List<Trabajador> listaTrabajadores();
    public void saveTrabajador(Trabajador trabajador);
    public Trabajador buscarTrabajadorPorId(Long id);
    public void eliminarTrabajadorPorId(Long id);
}
