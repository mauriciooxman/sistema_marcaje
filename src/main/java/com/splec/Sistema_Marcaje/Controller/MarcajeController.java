package com.splec.Sistema_Marcaje.Controller;

import com.splec.Sistema_Marcaje.Model.Marcaje;
import com.splec.Sistema_Marcaje.Service.MarcajeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/marcajes")
public class MarcajeController {
    public final MarcajeService marcajeService;

    public MarcajeController(MarcajeService marcajeService) {
        this.marcajeService = marcajeService;
    }

    @GetMapping
    public List<Marcaje> listaMarcaje(){
        return marcajeService.listaMarcajes();
    }

    @GetMapping("{id}")
    public Marcaje buscarMarcajePorId(@PathVariable Long id){
        return marcajeService.buscarMarcajePorId(id);
    }

    @PostMapping
    public void guardarMarcaje(@RequestBody Marcaje marcaje){
        marcajeService.guardarMarcajes(marcaje);
    }

    @PostMapping("/entrada/{trabajadorId}")
    public Marcaje registrarEntrada(@PathVariable Long trabajadorId){
        return marcajeService.registrarEntrada(trabajadorId);
    }
    @PostMapping("/salida/{trabajadorID}")
    public Marcaje registrarSalida(@PathVariable Long trabajadorId){
        return marcajeService.registrarSalida(trabajadorId);
    }

    @DeleteMapping("{id}")
    public void borrarMarcaje(@PathVariable Long id){
        marcajeService.eliminarMarcajePorId(id);
    }
}
