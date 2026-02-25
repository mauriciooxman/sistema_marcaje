package com.splec.Sistema_Marcaje.Controller;

import com.splec.Sistema_Marcaje.Dto.TrabajadorDTO;
import com.splec.Sistema_Marcaje.Model.Trabajador;
import com.splec.Sistema_Marcaje.Service.TrabajadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/trabajadores")
public class TrabajadorController {
    public final TrabajadorService trabajadorService;

    public TrabajadorController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping
    public List<TrabajadorDTO>listaTrabajadores(){
        return trabajadorService.listaTrabajadores();
    }

    @GetMapping("{id}")
    public TrabajadorDTO trabajador(@PathVariable Long id){
        return trabajadorService.buscarTrabajadorPorId(id);
    }

    @PostMapping
   public void saveTrabajador(@RequestBody Trabajador trabajador){
     trabajadorService.saveTrabajador(trabajador);
    }

    @DeleteMapping("{id}")
    public void deleteTrabajador(@PathVariable Long id){
        trabajadorService.eliminarTrabajadorPorId(id);
    }

}
