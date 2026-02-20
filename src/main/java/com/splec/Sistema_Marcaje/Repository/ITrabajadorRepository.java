package com.splec.Sistema_Marcaje.Repository;

import com.splec.Sistema_Marcaje.Model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITrabajadorRepository extends JpaRepository<Trabajador, Long> {
}
