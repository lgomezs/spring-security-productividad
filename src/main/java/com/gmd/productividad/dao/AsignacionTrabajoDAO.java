package com.gmd.productividad.dao;

import com.gmd.productividad.domain.AsignacionTrabajo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AsignacionTrabajoDAO extends GenericDAO<AsignacionTrabajo, Long> {
    public List<AsignacionTrabajo> find(String colaborador, String up, String tipoProceso, Date fechaInicio, Date fechaFin, Date fechaFinReal, List estados, Long idColaborador);
    public List<AsignacionTrabajo> findByColaborador(Long idColaborador);

    public Integer calcularHorasLaborables(Date fechaInicio, Date fechaFinEstimada);
}
