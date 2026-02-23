package com.splec.Sistema_Marcaje.Repository;

import com.splec.Sistema_Marcaje.Model.Marcaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMarcajeRepository extends JpaRepository<Marcaje, Long > {
    Optional<Marcaje> findTopByTrabajador_IdAndHoraSalidaIsNullOrderByFechaDesc(Long trabajadorId);

}
